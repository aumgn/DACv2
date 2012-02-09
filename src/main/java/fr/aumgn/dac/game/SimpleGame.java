package fr.aumgn.dac.game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.joinstep.JoinStage;
import fr.aumgn.dac.player.DACPlayer;

public class SimpleGame implements Game {

	private DACArena arena;
	private GameModeHandler gameModeHandler;
	private DACPlayer[] players;
	private int turn;
	
	public SimpleGame(GameMode gameMode, JoinStage joinStage) {
		this.arena = joinStage.getArena();
		List<DACPlayer> roulette = joinStage.getPlayers();
		players = new DACPlayer[roulette.size()];
		Random rand = new Random();
		for (int i=0; i< players.length; i++) {
			int j = rand.nextInt(roulette.size());
			DACPlayer dacPlayer = roulette.remove(j);
			DAC.getStageManager().unregisterPlayer(dacPlayer);
			dacPlayer = gameMode.createPlayer(this, dacPlayer);
			players[i] = dacPlayer;
			DAC.getStageManager().registerPlayer(dacPlayer);
		}
		gameModeHandler = gameMode.createHandler(this);
		turn = -1;
		gameModeHandler.onStart();
		nextTurn();
	}
	
	private void increaseTurn() {
		turn = (turn + 1) % players.length;
	}
	
	public void nextTurn() {
		increaseTurn();
		DACPlayer player = players[turn];
		gameModeHandler.onTurn(player);
	}
	
	private boolean isPlayerTurn(DACPlayer player) {
		return players[turn].equals(player);
	}
	
	public void send(String msg) {
		for (DACPlayer player : players) {
			player.getPlayer().sendMessage(msg);
		}
	}

	public void send(DACMessage msg) {
		send(msg.getValue());
	}
	
	@Override
	public DACArena getArena() {
		return arena;
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
	}

	@Override
	public void onFallDamage(EntityDamageEvent event) {
		Player player = (Player)event.getEntity();
		if (arena.getPool().isAbove(player)) {
			gameModeHandler.onFail(DAC.getStageManager().getPlayer(player));
			event.setCancelled(true);
		}
	}

	@Override
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		DACPlayer dacPlayer = DAC.getStageManager().getPlayer(player);
		if (isPlayerTurn(dacPlayer) && arena.getPool().contains(player)) {
			gameModeHandler.onSuccess(dacPlayer);
		}
	}

	@Override
	public void onQuit(PlayerQuitEvent event) {
	}

}
