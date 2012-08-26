package fr.aumgn.dac2.game.training;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdHashMap;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdMap;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.game.AbstractGame;
import fr.aumgn.dac2.game.GameParty;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.game.start.GameStartData.PlayerData;
import fr.aumgn.dac2.shape.column.Column;
import fr.aumgn.dac2.shape.column.ColumnPattern;
import fr.aumgn.dac2.shape.column.GlassyPattern;

public class Training extends AbstractGame {

    private GameParty<TrainingPlayer> party;
    private PlayersIdMap<TrainingPlayer> playersMap;

    public Training(DAC dac, GameStartData data) {
        super(dac, data);

        Map<PlayerId, ? extends GameStartData.PlayerData> playersData =
                data.getPlayersData();
        List<TrainingPlayer> list =
                new ArrayList<TrainingPlayer>(playersData.size());
        playersMap = new PlayersIdHashMap<TrainingPlayer>();

        for (Entry<PlayerId, ? extends PlayerData> entry :
                playersData.entrySet()) {
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
    public void start() {
        resetPoolOnStart();
        send("training.start");
        nextTurn();
    }

    private void nextTurn() {
        TrainingPlayer player = party.nextTurn();
        tpBeforeJump(player);
        send("training.playerturn", player.getDisplayName());
    }

    @Override
    public void stop(boolean force) {
        resetPoolOnEnd();
        for (TrainingPlayer player : party.iterable()) {
            player.sendStats(dac.getMessages());
        }
    }

    @Override
    public boolean contains(Player player) {
        return playersMap.containsKey(player);
    }

    @Override
    public void sendMessage(String message) {
        for (TrainingPlayer player : party.iterable()) {
            player.sendMessage(message);
        }
    }

    @Override
    public boolean isPlayerTurn(Player player) {
        TrainingPlayer trainingPlayer = playersMap.get(player);
        return trainingPlayer != null && party.isTurn(trainingPlayer);
    }

    @Override
    public void onJumpSuccess(Player player) {
        TrainingPlayer trainingPlayer = playersMap.get(player);
        World world = arena.getWorld();

        Column column = arena.getPool().getColumn(player);
        ColumnPattern pattern = trainingPlayer.getColumnPattern();

        if (column.isADAC(world)) {
            send("training.jump.dac", trainingPlayer.getDisplayName());
            trainingPlayer.incrementDacs();
            pattern = new GlassyPattern(pattern);
        } else {
            send("training.jump.success", trainingPlayer.getDisplayName());
            trainingPlayer.incrementSuccesses();
        }

        column.set(world, pattern);
        tpAfterJump(trainingPlayer);
        nextTurn();
    }

    @Override
    public void onJumpFail(Player player) {
        TrainingPlayer trainingPlayer = playersMap.get(player);
        send("training.jump.fail", trainingPlayer.getDisplayName());
        trainingPlayer.incrementFails();

        tpAfterJump(trainingPlayer);
        nextTurn();
    }

    @Override
    public void onQuit(Player player) {
        TrainingPlayer trainingPlayer = playersMap.get(player);
        party.removePlayer(trainingPlayer);
        playersMap.remove(player);
        trainingPlayer.sendStats(dac.getMessages());
    }
}
