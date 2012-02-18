package fr.aumgn.dac.api.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.event.game.DACGameFailEvent;
import fr.aumgn.dac.api.event.game.DACGameNewTurnEvent;
import fr.aumgn.dac.api.event.game.DACGameSuccessEvent;
import fr.aumgn.dac.api.event.stage.DACStagePlayerQuitEvent;
import fr.aumgn.dac.api.event.stage.DACStageStartEvent;
import fr.aumgn.dac.api.event.stage.DACStageStopEvent;
import fr.aumgn.dac.api.exception.PlayerCastException;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.game.mode.GameModeHandler;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StagePlayerManager;
import fr.aumgn.dac.api.util.DACUtil;

public class SimpleGame<T extends StagePlayer> implements Game<T> {

    private Arena arena;
    private GameMode<T> mode;
    private GameOptions options;
    private GameModeHandler<T> gameModeHandler;
    private T[] players;
    private Set<Player> spectators;
    private Vector propulsion;
    private int propulsionDelay;
    private int turn;
    private int turnTimeOutTaskId;
    private boolean finished;
    private Runnable turnTimeOutRunnable = new Runnable() {
        @Override
        public void run() {
            turnTimedOut();
        }
    };

    public SimpleGame(GameMode<T> gameMode, Stage<? extends StagePlayer> stage, GameOptions options) {
        this(gameMode, stage, stage.getPlayers(), options);
    }

    public SimpleGame(GameMode<T> gameMode, Stage<? extends StagePlayer> stage, List<? extends StagePlayer> playersList, GameOptions options) {
        stage.stop();
        this.arena = stage.getArena();
        this.mode = gameMode;
        this.options = options;
        parsePropulsion();
        List<StagePlayer> roulette = new ArrayList<StagePlayer>(playersList);
        players = shufflePlayers(gameMode, roulette);
        gameModeHandler = gameMode.createHandler(this);
        spectators = new HashSet<Player>();
        turn = players.length - 1;
        turnTimeOutTaskId = -1;
        finished = false;
        DAC.getStageManager().register(this);
        gameModeHandler.onStart();
        nextTurn();
        DAC.callEvent(new DACStageStartEvent(this));
    }

    private int parseInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException exc) {
            return 0;
        }
    }

    private void parsePropulsion() {
        String prop = options.get("propulsion", "0");
        String[] splitted = prop.split(",");
        if (splitted.length == 1) {
            propulsion = new Vector(0, parseInteger(splitted[0]), 0);
            propulsionDelay = 0;
        } else if (splitted.length == 2) {
            propulsion = new Vector(0, parseInteger(splitted[0]), 0);
            propulsionDelay = parseInteger(splitted[1]);
        } else if (splitted.length == 3) {
            propulsion = new Vector(
                    parseInteger(splitted[0]),
                    parseInteger(splitted[1]),
                    parseInteger(splitted[2]));
            propulsionDelay = 0;
        } else {
            propulsion = new Vector(
                    parseInteger(splitted[0]),
                    parseInteger(splitted[1]),
                    parseInteger(splitted[2]));
            propulsionDelay = parseInteger(splitted[3]);
        }
    }

    @SuppressWarnings("unchecked")
    private T[] shufflePlayers(GameMode<T> gameMode, List<? extends StagePlayer> roulette) {
        T[] ary = (T[]) new StagePlayer[roulette.size()];
        Random rand = DAC.getRand();
        for (int i = 0; i < ary.length; i++) {
            int j = rand.nextInt(roulette.size());
            StagePlayer dacPlayer = roulette.remove(j);
            ary[i] = gameMode.createPlayer(this, dacPlayer, i + 1);
        }
        return ary;
    }

    @SuppressWarnings("unchecked")
    private T castPlayer(StagePlayer player) {
        try {
            return (T) player;
        } catch (ClassCastException exc) {
            stop();
            throw new PlayerCastException(exc);
        }
    }

    @Override
    public void registerAll() {
        StagePlayerManager playerManager = DAC.getPlayerManager();
        for (StagePlayer player : players) {
            playerManager.register(player);
        }
    }

    @Override
    public void unregisterAll() {
        StagePlayerManager playerManager = DAC.getPlayerManager();
        for (StagePlayer player : players) {
            playerManager.unregister(player);
        }
    }

    private void increaseTurn() {
        turn++;
        if (turn == players.length) {
            turn = 0;
            DAC.callEvent(new DACGameNewTurnEvent(this));
            gameModeHandler.onNewTurn();
        }
    }

    @Override
    public void nextTurn() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        if (turnTimeOutTaskId != -1) {
            scheduler.cancelTask(turnTimeOutTaskId);
        }
        increaseTurn();
        if (!finished) {
            T player = players[turn];
            turnTimeOutTaskId = scheduler.scheduleAsyncDelayedTask(DAC.getPlugin(), turnTimeOutRunnable, DAC.getConfig().getTurnTimeOut());
            gameModeHandler.onTurn(player);
        }
    }

    private void turnTimedOut() {
        T player = players[turn];
        send(DACMessage.GameTurnTimedOut.format(player.getDisplayName()));
        removePlayer(player);
    }

    @Override
    public boolean isPlayerTurn(T player) {
        return !finished && players[turn].equals(player);
    }

    @Override
    public void send(Object msg, StagePlayer exclude) {
        for (StagePlayer player : players) {
            if (exclude == null || !player.getPlayer().equals(exclude.getPlayer())) {
                player.getPlayer().sendMessage(msg.toString());
            }
        }
        for (Player spectator : spectators) {
            spectator.sendMessage(msg.toString());
        }
    }

    @Override
    public void send(Object msg) {
        send(msg, null);
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public GameMode<T> getMode() {
        return mode;
    }

    @Override
    public GameOptions getOptions() {
        return options;
    }

    @Override
    public void removePlayer(StagePlayer player) {
        DAC.callEvent(new DACStagePlayerQuitEvent(this, player));
        gameModeHandler.onQuit(castPlayer(player));
        DAC.getPlayerManager().unregister(player);
    }

    @Override
    public List<T> getPlayers() {
        return Arrays.asList(players);
    }

    @Override
    public void stop() {
        finished = true;
        Bukkit.getScheduler().cancelTask(turnTimeOutTaskId);
        DAC.callEvent(new DACStageStopEvent(this));
        gameModeHandler.onStop();
        DAC.getStageManager().unregister(this);
        if (DAC.getConfig().getResetOnEnd()) {
            arena.getPool().reset();
        }
    }

    @Override
    public boolean addSpectator(Player player) {
        return spectators.add(player);
    }

    @Override
    public boolean removeSpectator(Player player) {
        return spectators.remove(player);
    }

    @Override
    public Vector getPropulsion() {
        return propulsion;
    }

    @Override
    public int getPropulsionDelay() {
        return propulsionDelay;
    }

    @Override
    public void onFallDamage(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();
        T gamePlayer = castPlayer(DAC.getPlayerManager().get(player));
        if (isPlayerTurn(gamePlayer) && arena.getPool().isAbove(player)) {
            DACGameFailEvent failEvent = new DACGameFailEvent(this, gamePlayer);
            DAC.callEvent(failEvent);

            if (!failEvent.isCancelled()) {
                gameModeHandler.onFail(gamePlayer);
            }

            if (failEvent.cancelDeath()) {
                event.setCancelled(true);
                int health = player.getHealth();
                if (health == DACUtil.PLAYER_MAX_HEALTH) {
                    player.damage(1);
                    player.setHealth(DACUtil.PLAYER_MAX_HEALTH);
                } else {
                    player.setHealth(health + 1);
                    player.damage(1);
                }
            }
        }
    }

    @Override
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        T gamePlayer = castPlayer(DAC.getPlayerManager().get(player));
        if (isPlayerTurn(gamePlayer) && arena.getPool().contains(player)) {
            DACGameSuccessEvent successEvent = new DACGameSuccessEvent(this, gamePlayer);
            DAC.callEvent(successEvent);
            if (!successEvent.isCancelled()) {
                gameModeHandler.onSuccess(gamePlayer);
            }
        }
    }

}
