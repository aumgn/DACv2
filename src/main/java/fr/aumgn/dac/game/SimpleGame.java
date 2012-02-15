package fr.aumgn.dac.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACUtil;
import fr.aumgn.dac.arena.DACArena;
import fr.aumgn.dac.event.game.DACGameFailEvent;
import fr.aumgn.dac.event.game.DACGameNewTurnEvent;
import fr.aumgn.dac.event.game.DACGameStartEvent;
import fr.aumgn.dac.event.game.DACGameStopEvent;
import fr.aumgn.dac.event.game.DACGameSuccessEvent;
import fr.aumgn.dac.exception.PlayerCastException;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.game.options.GameOptions;
import fr.aumgn.dac.joinstage.JoinStage;
import fr.aumgn.dac.stage.StageManager;
import fr.aumgn.dac.stage.StagePlayer;
import fr.aumgn.dac.stage.StagePlayersManager;

public class SimpleGame<T extends StagePlayer> implements Game<T> {

	private DACArena arena;
	private GameMode<T> mode;
	private GameOptions options;
	private GameModeHandler<T> gameModeHandler;
	private T[] players;
	private int turn;
	private boolean finished;
	
	@SuppressWarnings("unchecked")
	public SimpleGame(GameMode<T> gameMode, JoinStage<? extends StagePlayer> joinStage, GameOptions options) {
		StageManager stagesManager = DAC.getStageManager();
		joinStage.stop();
		this.arena = joinStage.getArena();
		this.mode = gameMode;
		this.options = options;
		List<StagePlayer> roulette = new ArrayList<StagePlayer>(joinStage.getPlayers());
		players = (T[]) new StagePlayer[roulette.size()];
		Random rand = DAC.getRand();
		for (int i=0; i< players.length; i++) {
			int j = rand.nextInt(roulette.size());
			StagePlayer dacPlayer = roulette.remove(j);
			players[i] = gameMode.createPlayer(this, dacPlayer, i);
		}
		gameModeHandler = gameMode.createHandler(this);
		turn = players.length - 1;
		finished = false;
		stagesManager.register(this);
		gameModeHandler.onStart();
		nextTurn();
		DAC.callEvent(new DACGameStartEvent(this));
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
		StagePlayersManager playerManager = DAC.getPlayerManager();
		for (StagePlayer player : players) {
			playerManager.register(player);
		}
	}

	@Override
	public void unregisterAll() {
		StagePlayersManager playerManager = DAC.getPlayerManager();
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
	
	public void nextTurn() {
		increaseTurn();
		if (!finished) {
			T player = players[turn];
			gameModeHandler.onTurn(player);
		}
	}
	
	public boolean isPlayerTurn(StagePlayer player) {
		return !finished && players[turn].equals(player);
	}
	
	public void send(Object msg, StagePlayer exclude) {
		for (StagePlayer player : players) {
			if (exclude == null || !player.getPlayer().equals(exclude.getPlayer())) {
				player.getPlayer().sendMessage(msg.toString());
			}
		}
	}
	
	public void send(Object msg) {
		send(msg, null);
	}
	
	@Override
	public DACArena getArena() {
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
		gameModeHandler.onQuit(castPlayer(player));
	}


	@Override
	public List<T> getPlayers() {
		return Arrays.asList(players);
	}

	@Override
	public void stop() {
		finished = true;
		DAC.callEvent(new DACGameStopEvent(this));
		gameModeHandler.onStop();
		DAC.getStageManager().unregister(this);
		if (DAC.getConfig().getResetOnEnd()) {
			arena.getPool().reset();
		}
	}

	@Override
	public void onFallDamage(EntityDamageEvent event) {
		Player player = (Player)event.getEntity();
		StagePlayer dacPlayer = DAC.getPlayerManager().get(player);
		if (isPlayerTurn(dacPlayer) && arena.getPool().isAbove(player)) {
			DACGameFailEvent failEvent = new DACGameFailEvent(this, dacPlayer);
			DAC.callEvent(failEvent);
			
			if (!failEvent.isCancelled()) {
				gameModeHandler.onFail(castPlayer(dacPlayer));
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
		StagePlayer dacPlayer = DAC.getPlayerManager().get(player);
		if (isPlayerTurn(dacPlayer) && arena.getPool().contains(player)) {
			DACGameSuccessEvent successEvent = new DACGameSuccessEvent(this, dacPlayer);
			DAC.callEvent(successEvent);
			if (!successEvent.isCancelled()) {
				gameModeHandler.onSuccess(castPlayer(dacPlayer));
			}
		}
	}

}
