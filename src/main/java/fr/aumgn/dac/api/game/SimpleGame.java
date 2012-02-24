package fr.aumgn.dac.api.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitScheduler;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.event.GameEvent;
import fr.aumgn.dac.api.game.event.GameFinish;
import fr.aumgn.dac.api.game.event.GameFinish.FinishReason;
import fr.aumgn.dac.api.game.event.GameJumpFail;
import fr.aumgn.dac.api.game.event.GameJumpSuccess;
import fr.aumgn.dac.api.game.event.GameLoose;
import fr.aumgn.dac.api.game.event.GameNewTurn;
import fr.aumgn.dac.api.game.event.GameQuit;
import fr.aumgn.dac.api.game.event.GameStart;
import fr.aumgn.dac.api.game.event.GameTurn;
import fr.aumgn.dac.api.game.event.GameWin;
import fr.aumgn.dac.api.game.messages.GameMessage;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.game.mode.GameHandler;
import fr.aumgn.dac.api.stage.SimpleStage;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StageQuitReason;

public class SimpleGame extends SimpleStage implements Game {

    private GameMode mode;
    private GameOptions options;
    private GameHandler gameHandler;
    private List<StagePlayer> players;
    private Set<Player> spectators;
    private Stack<String> ranking;
    private int turn;
    private int turnTimeOutTaskId;
    private boolean finished;

    private Runnable turnTimeOutRunnable = new Runnable() {
        @Override
        public void run() {
            turnTimedOut();
        }
    };

    private static List<StagePlayer> shuffle(List<? extends StagePlayer> roulette) {
        List<StagePlayer> list = new ArrayList<StagePlayer>(roulette.size());
        Random rand = DAC.getRand();
        int length = roulette.size();
        for (int i = 0; i < length; i++) {
            int j = rand.nextInt(roulette.size());
            StagePlayer player = roulette.remove(j);
            list.add(player);
        }
        return list;
    }

    public SimpleGame(GameMode gameMode, Stage stage, GameOptions options) {
        this(gameMode, stage, shuffle(stage.getPlayers()), options);
    }

    public SimpleGame(GameMode gameMode, Stage stage, List<? extends StagePlayer> playersList, GameOptions options) {
        super(stage.getArena());
        stage.stop();
        this.mode = gameMode;
        this.options = options;
        options.parsePropulsion();
        players = new ArrayList<StagePlayer>(playersList.size());
        int i = 0;
        for (StagePlayer player : playersList) {
            players.add(gameMode.createPlayer(this, player, i + 1));
            i++;
        }
        gameHandler = gameMode.createHandler();
        spectators = new HashSet<Player>();
        ranking = new Stack<String>();
        turn = players.size() - 1;
        turnTimeOutTaskId = -1;
        finished = false;
        DAC.getStageManager().register(this);
        GameStart start = new GameStart(this);
        gameHandler.onStart(start);
        if (start.getPoolReset()) {
            arena.getPool().reset();
        }
        sendEventMessages(start);
        nextTurn();
    }

    private void increaseTurn() {
        turn++;
        if (turn == players.size()) {
            turn = 0;
            GameNewTurn newTurn = new GameNewTurn(this);
            gameHandler.onNewTurn(newTurn);
            sendEventMessages(newTurn);
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
            StagePlayer player = players.get(turn);
            turnTimeOutTaskId = scheduler.scheduleAsyncDelayedTask(
                    DAC.getPlugin(), turnTimeOutRunnable, 
                    DAC.getConfig().getTurnTimeOut());
            GameTurn gameTurn = new GameTurn(player);
            gameHandler.onTurn(gameTurn);
            sendEventMessages(gameTurn);
            if (gameTurn.getTeleport()) {
                player.tpToDiving();
            }
        }
    }

    private void turnTimedOut() {
        StagePlayer player = players.get(turn);
        send(DACMessage.GameTurnTimedOut.format(player.getDisplayName()));
        removePlayer(player, StageQuitReason.TurnTimeOut);
    }

    @Override
    public boolean isPlayerTurn(StagePlayer player) {
        return !finished && players.get(turn).equals(player);
    }

    @Override
    public void send(Object msg) {
        super.send(msg);
        for (Player spectator : spectators) {
            spectator.sendMessage(ChatColor.BLUE + "[" + arena.getName() + "] " + msg.toString());
        }
    }

    private void sendEventMessages(GameEvent gameEvent) {
        for (GameMessage message : gameEvent.getMessages()) {
            message.send();
        }
    }

    private void onLoose(StagePlayer player, GameLoose loose) {
        ranking.add(player.getDisplayName());
        DAC.getPlayerManager().unregister(player);
        players.remove(player);
        gameHandler.onLoose(loose);
        sendEventMessages(loose);
        int min = mode.getClass().getAnnotation(DACGameMode.class).minPlayers();
        if (players.size() == min - 1) {
            if (players.size() == 1) {
                onWin(players.get(0));
            } else {
                stop(new GameFinish(this, FinishReason.NotEnoughPlayer));
            }
        }
    }

    private void stop(GameFinish finish) {
        finished = true;
        Bukkit.getScheduler().cancelTask(turnTimeOutTaskId);
        gameHandler.onFinish(finish);
        sendEventMessages(finish);
        DAC.getStageManager().unregister(this);
        if (finish.getPoolReset()) {
            arena.getPool().reset();
        }
    }

    @Override
    public GameMode getMode() {
        return mode;
    }

    @Override
    public GameOptions getOptions() {
        return options;
    }

    @Override
    public void onLoose(StagePlayer player) {
        addSpectator(player.getPlayer());      
        onLoose(player, new GameLoose(player));
    }

    @Override
    public void removePlayer(StagePlayer player, StageQuitReason reason) {
        onLoose(player, new GameQuit(player, reason));
    }

    @Override
    public List<StagePlayer> getPlayers() {
        return new ArrayList<StagePlayer>(players);
    }

    @Override
    public void stop() {
        stop(new GameFinish(this, FinishReason.Forced));
    }

    @Override
    public boolean canWatch(Player player) {
        StagePlayer stagePlayer = DAC.getPlayerManager().get(player);
        if (stagePlayer == null) {
            return true;
        }
        if (stagePlayer.getStage() == this) {
            return false;
        }
        return true;
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
    public void onFallDamage(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();
        StagePlayer stagePlayer = DAC.getPlayerManager().get(player);
        if (isPlayerTurn(stagePlayer) && arena.getPool().isSafe(player)) {
            int x = player.getLocation().getBlockX();
            int z = player.getLocation().getBlockZ();
            GameJumpFail jumpFail = new GameJumpFail(stagePlayer, x, z);
            gameHandler.onFail(jumpFail);
            if (!jumpFail.isCancelled()) {
                if (jumpFail.getCancelDeath()) {
                    event.setCancelled(true);
                    if (jumpFail.getMustTeleport()) {
                        stagePlayer.tpAfterFail();
                    }
                }
                if (jumpFail.getHasLost()) {
                    onLoose(stagePlayer);
                } else if (jumpFail.getSwitchToNextTurn()) {
                    nextTurn();
                }
                sendEventMessages(jumpFail);
            }
        }
    }

    @Override
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        StagePlayer stagePlayer = DAC.getPlayerManager().get(player);
        if (isPlayerTurn(stagePlayer) && arena.getPool().contains(player)) {
            int x = player.getLocation().getBlockX();
            int z = player.getLocation().getBlockZ();
            GameJumpSuccess jumpSuccess = new GameJumpSuccess(stagePlayer, x, z);
            gameHandler.onSuccess(jumpSuccess);
            if (!jumpSuccess.isCancelled()) {
                if (jumpSuccess.getMustTeleport()) {
                    stagePlayer.tpAfterJump();
                }
                if (jumpSuccess.getColumnPattern() != null) {
                    AreaColumn column = arena.getPool().getColumn(jumpSuccess.getPos());
                    column.set(jumpSuccess.getColumnPattern());
                }
                sendEventMessages(jumpSuccess);
                if (jumpSuccess.getSwitchToNextTurn()) {
                    nextTurn();
                }
            }
        }
    }

    @Override
    public void onWin(StagePlayer player) {
        stop(new GameWin(this, ranking));
    }

}
