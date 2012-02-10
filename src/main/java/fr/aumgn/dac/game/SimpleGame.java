package fr.aumgn.dac.game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACUtil;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.joinstep.JoinStage;
import fr.aumgn.dac.player.DACPlayer;

public class SimpleGame implements Game {

	private DACArena arena;
	private GameMode mode;
	private GameModeHandler gameModeHandler;
	private DACPlayer[] players;
	private int turn;
	
	public SimpleGame(GameMode gameMode, JoinStage joinStage) {
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
		gameModeHandler.onStart();
		nextTurn();
	}
	
	private void increaseTurn() {
		turn++;
		if (turn == players.length) {
			turn = 0;
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
	}


	@Override
	public List<DACPlayer> getPlayers() {
		return Arrays.asList(players);
	}

	@Override
	public void stop() {
		for (DACPlayer player : players) {
			DAC.getPlayerManager().unregister(player);
		}
		DAC.getStageManager().unregister(this);
		if (DAC.getConfig().getResetOnEnd()) {
			arena.getPool().reset();
		}
	}

	@Override
	public void onFallDamage(EntityDamageEvent event) {
		Player player = (Player)event.getEntity();
		if (arena.getPool().isAbove(player)) {
			gameModeHandler.onFail(DAC.getPlayerManager().get(player));
			int health = player.getHealth();
			if (health == DACUtil.PLAYER_MAX_HEALTH) {
				player.damage(1);
				player.setHealth(DACUtil.PLAYER_MAX_HEALTH);
			} else {
				player.setHealth(health + 1);
				player.damage(1);
			}
			event.setCancelled(true);
		}
	}

	@Override
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		DACPlayer dacPlayer = DAC.getPlayerManager().get(player);
		if (isPlayerTurn(dacPlayer) && arena.getPool().contains(player)) {
			gameModeHandler.onSuccess(dacPlayer);
		}
	}

	@Override
	public void onQuit(PlayerQuitEvent event) {
	}

}
