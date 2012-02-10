package fr.aumgn.dac.joinstage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACColor;
import fr.aumgn.dac.config.DACColors;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.event.joinstage.DACJoinStageStartEvent;
import fr.aumgn.dac.event.joinstage.DACJoinStageStopEvent;
import fr.aumgn.dac.event.joinstage.DACPlayerJoinEvent;
import fr.aumgn.dac.game.mode.DACGameMode;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.player.DACPlayerManager;

public class SimpleJoinStage implements JoinStage {
	
	private DACArena arena;
	private DACColors colors;
	private Set<DACColor> colorsMap;
	private List<DACPlayer> players;

	public SimpleJoinStage(DACArena arena) {
		this.arena = arena;
		colors = DAC.getConfig().getColors();
		colorsMap = new HashSet<DACColor>();
		players = new ArrayList<DACPlayer>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(DACMessage.JoinNewGame.format(arena.getName()));
			player.sendMessage(DACMessage.JoinNewGame2.getValue());
		}
		DAC.callEvent(new DACJoinStageStartEvent(this));
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

	public void send(Object msg, DACPlayer exclude) {
		for (DACPlayer player : players) {
			if (player != exclude) {
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
	
	private boolean isColorAvailable(String name) {
		DACColor color = colors.get(name);
		if (color == null) {
			return false;
		} else {
			return isColorAvailable(color);
		}
	}

	private boolean isColorAvailable(DACColor color) {
		return !colorsMap.contains(color);
	}
	
	private DACColor getFirstColorAvailable() {
		for (DACColor color : colors) {
			if (!colorsMap.contains(color)) {
				return color;
			}
		}
		// Should never be reached
		return colors.defaut();
	}
	
	private void addPlayer(Player player, DACColor color) {
		DACPlayerJoinEvent event = new DACPlayerJoinEvent(this, player, color, player.getLocation());
		DAC.callEvent(event);
		
		if (!event.isCancelled()) {
			DACPlayer dacPlayer = new JoinStagePlayer(this, player, event.getColor(), event.getStartLocation());
			players.add(dacPlayer);
			DAC.getPlayerManager().register(dacPlayer);
			colorsMap.add(color);
			dacPlayer.send(DACMessage.JoinCurrentPlayers);
			for (DACPlayer currentPlayer : players) {
				dacPlayer.send(DACMessage.JoinPlayerList.format(currentPlayer.getDisplayName()));
			}
			dacPlayer.sendToOthers(DACMessage.JoinPlayerJoin.format(dacPlayer.getDisplayName()));
		}
	}

	@Override
	public void addPlayer(Player player, String[] colorsName) {
		int i = 0;
		DACColor color;
		while (i < colorsName.length && !isColorAvailable(colorsName[i])) {
			i++; 
		}
		if (i == colorsName.length) {
			color = getFirstColorAvailable();
		} else {
			color = colors.get(colorsName[i]);
		}
		addPlayer(player, color);	
	}

	@Override
	public void removePlayer(DACPlayer player) {
		send(DACMessage.JoinPlayerQuit.format(player.getDisplayName()));
		players.remove(player);
		DAC.getPlayerManager().unregister(player);
		colorsMap.remove(player.getColor());
	}
	
	@Override
	public List<DACPlayer> getPlayers() {
		return new ArrayList<DACPlayer>(players);
	}

	@Override
	public void stop() {
		DAC.callEvent(new DACJoinStageStopEvent(this));
		DAC.getStageManager().unregister(this);
	}

	@Override
	public boolean isMinReached(GameMode mode) {
		int minimum = mode.getClass().getAnnotation(DACGameMode.class).minPlayers();
		return players.size() >= minimum;
	}

	@Override
	public boolean isMaxReached() {
		return (players.size() >= DAC.getConfig().getMaxPlayers());
	}

}
