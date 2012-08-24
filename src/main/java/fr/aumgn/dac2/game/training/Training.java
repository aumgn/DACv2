package fr.aumgn.dac2.game.training;

import static fr.aumgn.dac2.utils.DACUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdHashMap;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdMap;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GameListener;
import fr.aumgn.dac2.game.GameParty;
import fr.aumgn.dac2.stage.JoinPlayerData;
import fr.aumgn.dac2.stage.JoinStage;

public class Training implements Game {

    private final DAC dac;
    private final Arena arena;
    private final Listener listener;

    private GameParty<TrainingPlayer> party;
    private PlayersIdMap<TrainingPlayer> playersMap;

    public Training(DAC dac, JoinStage joinStage) {
        this.dac = dac;
        this.arena = joinStage.getArena();
        this.listener = new GameListener(this);

        Map<PlayerId, JoinPlayerData> joinDatas = joinStage.getPlayers();
        List<TrainingPlayer> list =
                new ArrayList<TrainingPlayer>(joinDatas.size());
        playersMap = new PlayersIdHashMap<TrainingPlayer>();

        for (Entry<PlayerId, JoinPlayerData> entry : joinDatas.entrySet()) {
            PlayerId playerId = entry.getKey();
            TrainingPlayer player =
                    new TrainingPlayer(playerId, entry.getValue());
            list.add(player);
            playersMap.put(playerId, player);
        }
        party = new GameParty<TrainingPlayer>(this, TrainingPlayer.class,
                list);
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public void start() {
        if (dac.getConfig().getResetOnStart()) {
            arena.getPool().reset(arena.getWorld());
        }

        send("training.start");
        nextTurn();
    }

    private void nextTurn() {
        TrainingPlayer player = party.nextTurn();
        tpBeforeJump(player);
        send("training.playerturn", player.getDisplayName());
    }

    private void tpBeforeJump(TrainingPlayer player) {
        if (dac.getConfig().getTpBeforeJump()) {
            player.teleport(arena.getDiving().toLocation(arena.getWorld()));
        }
    }

    private void tpAfterJump(final TrainingPlayer player) {
        if (dac.getConfig().getTpAfterJump()) {
            int delay = dac.getConfig().getTpAfterSuccessDelay();
            if (delay > 0) {
                player.setNoDamageTicks(TICKS_PER_SECONDS);
                Bukkit.getScheduler().scheduleSyncDelayedTask(dac.getPlugin(),
                        new Runnable() {
                    @Override
                    public void run() {
                        player.tpToStart();
                    }
                });
            } else {
                player.tpToStart();
            }
        } else {
            player.setNoDamageTicks(TICKS_PER_SECONDS);
        }
    }

    @Override
    public void stop(boolean force) {
        for (TrainingPlayer player : party.iterable()) {
            player.sendStats(dac.getMessages());
        }
    }

    @Override
    public Listener[] getListeners() {
        return new Listener[] { listener };
    }

    @Override
    public boolean contains(Player player) {
        return false;
    }

    @Override
    public void sendMessage(String message) {
        for (TrainingPlayer player : party.iterable()) {
            player.sendMessage(message);
        }
    }

    private void send(String key, Object... arguments) {
        sendMessage(dac.getMessages().get(key, arguments));
    }

    @Override
    public void onNewTurn() {
    }

    @Override
    public boolean isPlayerTurn(Player player) {
        TrainingPlayer trainingPlayer = playersMap.get(player);
        return trainingPlayer != null && party.isTurn(trainingPlayer);
    }

    @Override
    public void onJumpSuccess(Player player) {
        World world = arena.getWorld();
        Pool pool = arena.getPool();
        Vector2D pos = new Vector2D(player.getLocation().getBlock());
        TrainingPlayer trainingPlayer = playersMap.get(player);

        if (pool.isADAC(world, pos)) {
            send("training.jump.dac", trainingPlayer.getDisplayName());
            pool.putDACColumn(world, pos, trainingPlayer.color);
        } else {
            send("training.jump.success", trainingPlayer.getDisplayName());
            pool.putColumn(world, pos, trainingPlayer.color);
        }

        tpAfterJump(trainingPlayer);
        nextTurn();
    }

    @Override
    public void onJumpFail(Player player) {
        TrainingPlayer trainingPlayer = playersMap.get(player);
        send("training.jump.fail", trainingPlayer.getDisplayName());

        tpAfterJump(trainingPlayer);
        nextTurn();
    }

    @Override
    public void onQuit(Player player) {
        TrainingPlayer trainingPlayer = playersMap.get(player);
        party.removePlayer(trainingPlayer);
        trainingPlayer.sendStats(dac.getMessages());
    }
}
