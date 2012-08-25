package fr.aumgn.dac2.game.classic;

import static fr.aumgn.dac2.utils.DACUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.bukkitutils.playerid.list.PlayersIdArrayList;
import fr.aumgn.bukkitutils.playerid.list.PlayersIdList;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdHashMap;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdMap;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.game.AbstractGame;
import fr.aumgn.dac2.game.GameParty;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.game.start.GameStartData.PlayerData;
import fr.aumgn.dac2.game.GameTimer;

public class ClassicGame extends AbstractGame {

    private final GameParty<ClassicGamePlayer> party;
    private final PlayersIdMap<ClassicGamePlayer> playersMap;
    private final PlayersIdList spectators;
    private final ClassicGamePlayer[] rank;

    private final Runnable turnTimedOut = new Runnable() {
        @Override
        public void run() {
            turnTimedOut();
        }
    };

    private boolean finished;
    private GameTimer timer;

    public ClassicGame(DAC dac, GameStartData data) {
        super(dac, data);

        Map<PlayerId, ? extends PlayerData> playersData = data.getPlayersData();
        List<ClassicGamePlayer> list =
                new ArrayList<ClassicGamePlayer>(playersData.size());
        playersMap = new PlayersIdHashMap<ClassicGamePlayer>();

        for (Entry<PlayerId, ? extends PlayerData> entry :
                playersData.entrySet()) {
            PlayerId playerId = entry.getKey();
            ClassicGamePlayer player = new ClassicGamePlayer(playerId,
                    playersData.get(playerId));
            list.add(player);
            playersMap.put(playerId, player);
        }
        party = new GameParty<ClassicGamePlayer>(this, ClassicGamePlayer.class,
                list);

        spectators = new PlayersIdArrayList();
        spectators.addAll(data.getSpectators());
        rank = new ClassicGamePlayer[party.size() - 1];
    }

    @Override
    public void start() {
        if (dac.getConfig().getResetOnStart()) {
            arena.getPool().reset(arena.getWorld());
        }

        send("game.start");
        send("game.playerslist");
        for (ClassicGamePlayer player : party.iterable()) {
            send("game.playerentry", player.getIndex() + 1, player.getDisplayName());
        }
        send("game.enjoy");

        nextTurn();
    }

    @Override
    public boolean contains(Player player) {
        return playersMap.containsKey(player);
    }

    @Override
    public void sendMessage(String message) {
        for (ClassicGamePlayer player : party.iterable()) {
            player.sendMessage(message);
        }
        for (Player spectator : spectators.players()) {
            spectator.sendMessage(message);
        }
    }

    private void send(String key, Object... arguments) {
        sendMessage(dac.getMessages().get(key, arguments));
    }

    private void nextTurn() {
        ClassicGamePlayer player = party.nextTurn();

        if (timer != null) {
            timer.cancel();
        }
        timer = new GameTimer(dac, this, turnTimedOut);

        if (!player.isOnline()) {
            send("game.playerturn.notconnected", player.getDisplayName());
            removePlayer(player);
            if (isConfirmationTurn()) {
                send("game.jump.confirmationfail");
                for (ClassicGamePlayer deadPlayer : party.iterable()) {
                    deadPlayer.incrementLives();
                }
            }
            nextTurn();
        } else {
            send("game.playerturn", player.getDisplayName());
            if (isConfirmationTurn()) {
                send("game.confirmationneeded");
            }
            tpBeforeJump(player);
            timer.start();
        }
    }

    private boolean isConfirmationTurn() {
        if (!party.isLastTurn()) {
            return false;
        }

        int remaining = 0;
        for (ClassicGamePlayer player : party.iterable()) {
            if (!player.isDead()) {
                remaining++;
            }
        }

        return remaining == 1;
    }

    private void turnTimedOut() {
        ClassicGamePlayer player = party.getCurrent();
        send("game.turn.timedout", player.getDisplayName());
        removePlayer(player);
        if (!finished) {
            nextTurn();
        }
    }

    @Override
    public boolean isPlayerTurn(Player player) {
        ClassicGamePlayer gamePlayer = playersMap.get(player);
        return gamePlayer != null && party.isTurn(gamePlayer);
    }

    private void removePlayer(ClassicGamePlayer player) {
        party.removePlayer(player);
        playersMap.remove(player.playerId);
        addToRank(player);
        spectators.add(player.playerId);

        if (party.size() == 1) {
            onPlayerWin(party.getCurrent());
        }
    }

    private void addToRank(ClassicGamePlayer player) {
        for (int i = rank.length; i >= 0; i--) {
            if (rank[i] == null) {
                rank[i] = player;
                return;
            }
        }
    }

    @Override
    public void onJumpSuccess(Player player) {
        ClassicGamePlayer gamePlayer = playersMap.get(player);
        World world = arena.getWorld();
        Pool pool = arena.getPool();

        Vector2D pos = new Vector2D(player.getLocation().getBlockX(),
                player.getLocation().getBlockZ());
        player.setFallDistance(0.0f);

        if (isConfirmationTurn()) {
            if (pool.isADAC(world, pos)) {
                send("game.jump.dacconfirmation", gamePlayer.getDisplayName());
                send("game.jump.dacconfirmation2");
                pool.putDACColumn(world, pos, gamePlayer.color);
            } else {
                send("game.jump.confirmation", gamePlayer.getDisplayName());
                pool.putColumn(world, pos, gamePlayer.color);
            }
            for (ClassicGamePlayer deadPlayer : party.iterable()) {
                if (deadPlayer.isDead()) {
                    removePlayer(deadPlayer);
                }
            }
            onPlayerWin(gamePlayer);
        } else {
            if (pool.isADAC(world, pos)) {
                send("game.jump.dac", gamePlayer.getDisplayName());
                gamePlayer.incrementLives();
                send("game.livesafterdac", gamePlayer.getLives());
                pool.putDACColumn(world, pos, gamePlayer.color);
            } else {
                send("game.jump.success", gamePlayer.getDisplayName());
                pool.putColumn(world, pos, gamePlayer.color);
            }
        }

        tpAfterJump(gamePlayer);
        nextTurn();
    }

    @Override
    public void onJumpFail(Player player) {
        int health = player.getHealth(); 
        if (health == PLAYER_MAX_HEALTH) {
            player.damage(1);
            player.setHealth(PLAYER_MAX_HEALTH);
        } else {
            player.setHealth(health + 1);
            player.damage(1);
        }

        Vector2D pos = new Vector2D(player.getLocation().getBlock());
        ClassicGamePlayer gamePlayer = playersMap.get(player);

        send("game.jump.fail", gamePlayer.getDisplayName());
        if (isConfirmationTurn()) {
            send("game.jump.confirmationfail");
            for (ClassicGamePlayer deadPlayer : party.iterable()) {
                if (deadPlayer.isDead()) {
                    deadPlayer.incrementLives();
                }
            }
        } else {
            gamePlayer.onFail(pos);
            if (gamePlayer.isDead()) {
                onPlayerLoss(gamePlayer);
                if (!finished) {
                    tpAfterJump(gamePlayer);
                    nextTurn();
                }
            } else {
                send("game.livesafterfail", gamePlayer.getLives());
            }
        }
    }

    @Override
    public void onQuit(Player player) {
        ClassicGamePlayer gamePlayer = playersMap.get(player);
        send("game.player.quit", gamePlayer.getDisplayName());
        removePlayer(gamePlayer);
    }

    public void onPlayerLoss(ClassicGamePlayer player) {
        removePlayer(player);
    }

    public void onPlayerWin(ClassicGamePlayer player) {
        send("game.finished");
        send("game.winner", player.getDisplayName());
        for (int i = 0; i < rank.length; i++) {
            send("game.rank", i + 2, player.getDisplayName());
        }
        dac.getStages().stop(this);
    }

    public void stop(boolean force) {
        if (timer != null) {
            timer.cancel();
        }

        finished = true;
        if (dac.getConfig().getResetOnEnd()) {
            arena.getPool().reset(arena.getWorld());
        }

        if (force) {
            send("game.stopped");
        }
    }
}
