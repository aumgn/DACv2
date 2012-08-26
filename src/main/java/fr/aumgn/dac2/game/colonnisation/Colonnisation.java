package fr.aumgn.dac2.game.colonnisation;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdHashMap;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdMap;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.game.AbstractGame;
import fr.aumgn.dac2.game.GameParty;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.game.start.GameStartData.PlayerData;
import fr.aumgn.dac2.shape.column.Column;
import fr.aumgn.dac2.shape.column.ColumnPattern;
import fr.aumgn.dac2.shape.column.GlassyPattern;

public class Colonnisation extends AbstractGame {

    private final GameParty<ColonnPlayer> party;
    private final PlayersIdMap<ColonnPlayer> playersMap;

    public Colonnisation(DAC dac, GameStartData data) {
        super(dac, data);

        Map<PlayerId, ? extends PlayerData> playersData =
                data.getPlayersData();
        List<ColonnPlayer> list =
                new ArrayList<ColonnPlayer>(playersData.size());
        playersMap = new PlayersIdHashMap<ColonnPlayer>();

        for (Entry<PlayerId, ? extends PlayerData> entry :
                playersData.entrySet()) {
            PlayerId playerId = entry.getKey();
            ColonnPlayer player = new ColonnPlayer(playerId,
                    playersData.get(playerId));
            list.add(player);
            playersMap.put(playerId, player);
        }
        party = new GameParty<ColonnPlayer>(this,
                ColonnPlayer.class, list);
    }

    @Override
    public void start() {
        resetPoolOnStart();
        send("colonnisation.start");
        send("colonnisation.playerslist");
        for (ColonnPlayer player : party.iterable()) {
            send("colonnisation.playerentry", player.getIndex() + 1,
                    player.getDisplayName());
        }
        send("colonnisation.enjoy");
        nextTurn();
    }

    private void nextTurn() {
        ColonnPlayer player = party.nextTurn();
        send("colonnisation.playerturn", player.getDisplayName());
        tpBeforeJump(player);
    }

    @Override
    public void stop(boolean force) {
        resetPoolOnEnd();

        if (force) {
            send("colonnisation.stopped");
        } else {
            send("colonnisation.finished");
        }

        ColonnPlayer[] ranking = party.iterable().clone();
        Arrays.sort(ranking);

        int index = ranking.length - 1;
        send("colonnisation.winner", ranking[index].getDisplayName());
        index--;
        for (; index >= 0; index--) {
            send("colonnisation.ranking", ranking.length - index,
                    ranking[index].getDisplayName());
        }
    }

    @Override
    public boolean contains(Player player) {
        return playersMap.containsKey(player);
    }

    @Override
    public void sendMessage(String message) {
        for (ColonnPlayer player : party.iterable()) {
            player.sendMessage(message);
        }
    }

    @Override
    public boolean isPlayerTurn(Player player) {
        ColonnPlayer gamePlayer = playersMap.get(player);
        return gamePlayer != null && party.isTurn(gamePlayer);
    }

    @Override
    public void onJumpSuccess(Player player) {
        ColonnPlayer gamePlayer = playersMap.get(player);
        World world = arena.getWorld();
        Pool pool = arena.getPool();

        Column column = pool.getColumn(player);
        ColumnPattern pattern = gamePlayer.getColumnPattern();
        boolean isADAC = column.isADAC(world);
        if (isADAC) {
            gamePlayer.incrementMultiplier();
            pattern = new GlassyPattern(pattern);
        }
        column.set(world, pattern);

        PoolVisitor visitor = new PoolVisitor(world, pool,
                gamePlayer.getColor());
        int points = visitor.visit(column.getPos());
        points *= gamePlayer.getMultiplier();
        gamePlayer.addPoints(points);

        if (isADAC) {
            send("colonnisation.multiplier.increment",
                    gamePlayer.getMultiplier());
        }
        send("colonnisation.jump.success", gamePlayer.getDisplayName(), points,
                gamePlayer.getScore());

        if (pool.isFilled(world)) {
            dac.getStages().stop(this);
        } else {
            tpAfterJump(gamePlayer);
            nextTurn();
        }
    }

    @Override
    public void onJumpFail(Player player) {
        ColonnPlayer gamePlayer = playersMap.get(player);

        send("colonnisation.jump.fail", gamePlayer.getDisplayName());
        if (gamePlayer.getMultiplier() > 1) {
            gamePlayer.resetMultiplier();
            send("colonnisation.multiplier.reset");
        }

        tpAfterJump(gamePlayer);
        nextTurn();
    }

    @Override
    public void onQuit(Player player) {
        ColonnPlayer gamePlayer = playersMap.get(player);

        send("colonnisation.player.quit", gamePlayer.getDisplayName(),
                gamePlayer.getScore());
    }
}
