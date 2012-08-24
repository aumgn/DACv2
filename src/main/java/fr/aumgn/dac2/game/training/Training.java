package fr.aumgn.dac2.game.training;

import static fr.aumgn.dac2.utils.DACUtil.*;

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
import fr.aumgn.dac2.stage.JoinPlayerData;
import fr.aumgn.dac2.stage.JoinStage;

public class Training implements Game {

    private final DAC dac;
    private final Arena arena;
    private final Listener listener;

    private TrainingPlayer[] players;
    private PlayersIdMap<TrainingPlayer> playersMap;
    private int turn;

    public Training(DAC dac, JoinStage joinStage) {
        this.dac = dac;
        this.arena = joinStage.getArena();
        this.listener = new GameListener(this);

        Map<PlayerId, JoinPlayerData> joinDatas = joinStage.getPlayers();
        this.players = new TrainingPlayer[joinDatas.size()];
        this.playersMap = new PlayersIdHashMap<TrainingPlayer>();

        int i = 0;
        for (Entry<PlayerId, JoinPlayerData> entry : joinDatas.entrySet()) {
            PlayerId playerId = entry.getKey();
            players[i] = new TrainingPlayer(playerId, entry.getValue());
            playersMap.put(playerId, players[i]);
            i++;
        }
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
        turn = -1;
        nextTurn();
    }

    private void incrementTurn() {
        turn++;
        if (turn >= players.length) {
            turn = 0;
        }
    }

    private void nextTurn() {
        incrementTurn();
        TrainingPlayer player = players[turn];
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
        for (TrainingPlayer player : players) {
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
        for (TrainingPlayer player : players) {
            player.sendMessage(message);
        }
    }

    private void send(String key, Object... arguments) {
        sendMessage(dac.getMessages().get(key, arguments));
    }

    @Override
    public boolean isPlayerTurn(Player player) {
        TrainingPlayer trainingPlayer = playersMap.get(player);
        return trainingPlayer == players[turn];
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
        removePlayer(trainingPlayer);
        trainingPlayer.sendStats(dac.getMessages());
    }

    private void removePlayer(TrainingPlayer trainingPlayer) {
        int index;
        for (index = 0; index < players.length; index++) {
            if (players[index] == trainingPlayer) {
                break;
            }
        }

        TrainingPlayer[] newPlayers = new TrainingPlayer[players.length - 1];
        System.arraycopy(players, 0, newPlayers, 0, index);
        System.arraycopy(players, index + 1, newPlayers,
                index, players.length - index - 1);

        if (index <= turn) {
            turn--;
        }

        players = newPlayers;
        playersMap.remove(trainingPlayer.playerId);
    }
}
