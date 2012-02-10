package fr.aumgn.dac.game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACUtil;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.event.game.DACGameFailEvent;
import fr.aumgn.dac.event.game.DACGameNewTurnEvent;
import fr.aumgn.dac.event.game.DACGameStartEvent;
import fr.aumgn.dac.event.game.DACGameStopEvent;
import fr.aumgn.dac.event.game.DACGameSuccessEvent;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.joinstage.JoinStage;
import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.player.DACPlayerManager;
import fr.aumgn.dac.stage.StageManager;

public class SimpleGame implements Game {

	private DACArena arena;
	private GameMode mode;
	private GameModeHandler gameModeHandler;
	private DACPlayer[] players;
	private int turn;
	
	public SimpleGame(GameMode gameMode, JoinStage joinStage) {
		StageManager stagesManager = DAC.getStageManager();
		stagesManager.unregister(joinStage);
		this.arena = joinStage.getArena();
		this.mode = gameMode;
		List<DACPlayer> roulette = joinStage.getPlayers();
		players = new DACPlayer[roulette.size()];
		Random rand = new Random();
		for (int i=0; i< players.length; i++) {
			int j = rand.nextInt(roulette.size());
			DACPlayer dacPlayer = roulette.remove(j);
			DAC.getPlayerManager().unregister(dacPlayer);
			dacPlayer = gameMode.createPlayer(this, dacPlayer, i);
			players[i] = dacPlayer;
			DAC.getPlayerManager().register(dacPlayer);
		}
		gameModeHandler = gameMode.createHandler(this);
		turn = -1;
		stagesManager.register(this);
		gameModeHandler.onStart();
		nextTurn();
		DAC.callEvent(new DACGameStartEvent(this));
	}
	
	@Override
	public void registerAll() {
		DACPlayerManager playerManager = DAC.getPlayerManager();
		for (DACPlayer player : players) {
			playerManager.register(player);
		}
	}

	@Override
	public void unregisterAll() {
		DACPlayerManager playerManager = DAC.getPlayerManager();
		for (DACPlayer player : players) {
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
		DACPlayer player = players[turn];
		gameModeHandler.onTurn(player);
	}
	
	public boolean isPlayerTurn(DACPlayer player) {
		return players[turn].equals(player);
	}
	
	public void send(Object msg, DACPlayer exclude) {
		for (DACPlayer player : players) {
			//if (player != exclude) {
				player.getPlayer().sendMessage(msg.toString());
			//}
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
	public GameMode getMode() {
		return mode;
	}

	@Override
	public void removePlayer(DACPlayer player) {
		gameModeHandler.onQuit(player);
	}


	@Override
	public List<DACPlayer> getPlayers() {
		return Arrays.asList(players);
	}

	@Override
	public void stop() {
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
		if (arena.getPool().isAbove(player)) {
			DACPlayer dacPlayer = DAC.getPlayerManager().get(player);
			DACGameFailEvent failEvent = new DACGameFailEvent(this, dacPlayer);
			DAC.callEvent(failEvent);
			
			if (!failEvent.isCancelled()) {
				gameModeHandler.onFail(dacPlayer);
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
		DACPlayer dacPlayer = DAC.getPlayerManager().get(player);
		if (isPlayerTurn(dacPlayer) && arena.getPool().contains(player)) {
			DACGameSuccessEvent successEvent = new DACGameSuccessEvent(this, dacPlayer);
			DAC.callEvent(successEvent);
			if (!successEvent.isCancelled()) {
				gameModeHandler.onSuccess(dacPlayer);
			}
		}
	}

}
