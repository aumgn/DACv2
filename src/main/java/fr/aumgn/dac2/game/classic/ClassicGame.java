package fr.aumgn.dac2.game.classic;

import static fr.aumgn.dac2.utils.DACUtil.PLAYER_MAX_HEALTH;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefHashMap;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefMap;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.game.AbstractGame;
import fr.aumgn.dac2.game.GameParty;
import fr.aumgn.dac2.game.GameTimer;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.game.suddendeath.SuddenDeath;
import fr.aumgn.dac2.shape.column.Column;
import fr.aumgn.dac2.shape.column.ColumnPattern;
import fr.aumgn.dac2.shape.column.GlassyPattern;
import fr.aumgn.dac2.stage.StagePlayer;

public class ClassicGame extends AbstractGame {

    private final GameParty<ClassicGamePlayer> party;
    private final PlayersRefMap<ClassicGamePlayer> playersMap;
    private final ClassicGamePlayer[] ranking;

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

        Set<? extends StagePlayer> players = data.getPlayers();
        List<ClassicGamePlayer> list =
                new ArrayList<ClassicGamePlayer>(players.size());
        playersMap = new PlayersRefHashMap<ClassicGamePlayer>();

        for (StagePlayer stagePlayer : players) {
            ClassicGamePlayer player = new ClassicGamePlayer(stagePlayer);
            list.add(player);
            playersMap.put(player.getRef(), player);
        }
        party = new GameParty<ClassicGamePlayer>(this, ClassicGamePlayer.class,
                list);

        ranking = new ClassicGamePlayer[party.size() - 1];
    }

    @Override
    public void start() {
        resetPoolOnStart();

        send("game.start");
        send("game.playerslist");
        for (ClassicGamePlayer player : party.iterable()) {
            send("game.start.playerentry", player.getIndex() + 1,
                    player.getDisplayName());
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
        sendSpectators(message);
    }

    @Override
    public void onNewTurn() {
        for (ClassicGamePlayer player : party.iterable().clone()) {
            if (player.isDead()) {
                removePlayer(player);
            }
        }
    }

    private void nextTurn() {
        ClassicGamePlayer player = party.nextTurn();
        if (finished) {
            return;
        }

        if (timer != null) {
            timer.cancel();
        }
        timer = new GameTimer(dac, this, turnTimedOut);

        if (!player.isOnline()) {
            send("game.playerturn.notconnected", player.getDisplayName());
            removePlayer(player);
            if (!finished) {
                if (isConfirmationTurn()) {
                    send("game.jump.confirmationfail");
                    for (ClassicGamePlayer deadPlayer : party.iterable()) {
                        deadPlayer.incrementLives();
                    }
                }
                nextTurn();
            }
        } else {
            if (isConfirmationTurn()) {
                send("game.confirmationneeded", player.getDisplayName());
            } else {
                send("game.playerturn", player.getDisplayName());
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
        playersMap.remove(player.getRef());
        addToRanking(player);
        spectators.add(player.getRef());

        if (party.size() == 1) {
            onPlayerWin(party.getCurrent());
        }
    }

    private void addToRanking(ClassicGamePlayer player) {
        for (int i = ranking.length - 1; i >= 0; i--) {
            if (ranking[i] == null) {
                ranking[i] = player;
                return;
            }
        }
    }

    @Override
    public void onJumpSuccess(Player player) {
        ClassicGamePlayer gamePlayer = playersMap.get(player);
        World world = arena.getWorld();

        Column column = arena.getPool().getColumn(player);
        boolean isADAC = column.isADAC(world);
        ColumnPattern pattern = gamePlayer.getColumnPattern();
        if (isADAC) {
            pattern = new GlassyPattern(pattern);
        }
        column.set(world, pattern);

        if (isConfirmationTurn()) {
            if (isADAC) {
                send("game.jump.dacconfirmation", gamePlayer.getDisplayName());
                send("game.jump.dacconfirmation2");
            } else {
                send("game.jump.confirmation", gamePlayer.getDisplayName());
            }
            for (ClassicGamePlayer deadPlayer : party.iterable()) {
                if (deadPlayer.isDead()) {
                    removePlayer(deadPlayer);
                }
                if (finished) {
                    tpAfterJumpSuccess(gamePlayer, column);
                    return;
                }
            }
            onPlayerWin(gamePlayer);
        } else {
            if (isADAC) {
                send("game.jump.dac", gamePlayer.getDisplayName());
                gamePlayer.incrementLives();
                send("game.livesafterdac", gamePlayer.getLives());
            } else {
                send("game.jump.success", gamePlayer.getDisplayName());
            }
        }

        tpAfterJumpSuccess(gamePlayer, column);
        if (arena.getPool().isFilled(world)) {
            onPoolFilled();
        } else {
            nextTurn();
        }
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
            if (!gamePlayer.isDead()) {
                send("game.livesafterfail", gamePlayer.getLives());
            }
        }

        tpAfterJumpFail(gamePlayer);
        nextTurn();
    }

    @Override
    public void onQuit(Player player) {
        ClassicGamePlayer gamePlayer = playersMap.get(player);
        send("game.player.quit", gamePlayer.getDisplayName());
        removePlayer(gamePlayer);
    }

    public void onPoolFilled() {
        int maxLives = -1;
        Set<ClassicGamePlayer> players = new HashSet<ClassicGamePlayer>();
        for (ClassicGamePlayer player : party.iterable()) {
            if (player.getLives() < maxLives) {
                onPlayerLoss(player);
                continue;
            } else if (player.getLives() > maxLives) {
                maxLives = player.getLives();
                for (ClassicGamePlayer looser : players) {
                    onPlayerLoss(looser);
                }
                players.clear();
            }

            players.add(player);
        }

        if (!finished) {
            ToSuddenDeath data = new ToSuddenDeath(arena, players, spectators);
            SuddenDeath suddenDeath = new SuddenDeath(dac, data);
            dac.getStages().switchTo(suddenDeath);
        }
    }

    public void onPlayerLoss(ClassicGamePlayer player) {
        removePlayer(player);
    }

    public void onPlayerWin(ClassicGamePlayer player) {
        send("game.finished");
        send("game.winner", player.getDisplayName());
        for (int i = 0; i < ranking.length; i++) {
            send("game.rank", i + 2, ranking[i].getDisplayName());
        }
        dac.getStages().stop(this);
    }

    public void stop(boolean force) {
        if (timer != null) {
            timer.cancel();
        }

        finished = true;
        resetPoolOnEnd();

        if (force) {
            send("game.stopped");
        }
    }

    @Override
    public void list(CommandSender sender) {
        PluginMessages messages = dac.getMessages();

        sender.sendMessage(messages.get("game.playerslist"));
        for (ClassicGamePlayer player : party.iterable()) {
            sender.sendMessage(messages.get("game.playerentry", 
                    player.getIndex(), player.getDisplayName(), 
                    player.getLives()));
        }
    }
}
