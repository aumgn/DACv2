package fr.aumgn.dac2.game.colonnisation;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdHashMap;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdMap;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.game.AbstractGame;
import fr.aumgn.dac2.game.GameParty;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.game.start.GameStartData.PlayerData;

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
        Pool pool = arena.getPool();
        World world = arena.getWorld();

        Vector2D pos = new Vector2D(player.getLocation().getBlockX(),
                player.getLocation().getBlockZ());

        boolean isADAC = pool.isADAC(world, pos);
        if (isADAC) {
            gamePlayer.incrementMultiplier();
            pool.putDACColumn(world, pos, gamePlayer.color);
        } else {
            pool.putColumn(world, pos, gamePlayer.color);
        }

        PoolVisitor visitor = new PoolVisitor(arena, gamePlayer.color);
        int points = visitor.visit(pos) * gamePlayer.getMultiplier();
        gamePlayer.addPoints(points);

        if (isADAC) {
            send("colonnisation.multiplier.increment",
                    gamePlayer.getDisplayName(), gamePlayer.getMultiplier());
        }
        send("colonnisation.jump.success", gamePlayer.getDisplayName(), points,
                gamePlayer.getScore());

        tpAfterJump(gamePlayer);
        nextTurn();
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
