package fr.aumgn.dac;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACColors;
import fr.aumgn.dac.config.DACColor;
import fr.aumgn.dac.config.DACMessage;

public class DACJoinStep {
	
	private DACArena arena;
	private DACColors colors;
	private Set<DACColor> colorsMap;
	private List<DACPlayer> players;

	public DACJoinStep(DACArena arena) {
		this.arena = arena;
		colors = DAC.getDACConfig().getColors();
		colorsMap = new HashSet<DACColor>();
		players = new ArrayList<DACPlayer>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(DACMessage.JoinNewGame.format(arena.getName()));
			player.sendMessage(DACMessage.JoinNewGame2.getValue());
		}
	}
	
	public DACArena getArena() {
		return arena;
	}

	public List<DACPlayer> getPlayers() {
		return players;
	}

	public void notify(String message) {
		for (DACPlayer player : players) {
			player.getPlayer().sendMessage(message);
		}
	}
	
	public void notify(DACMessage message) {
		notify(message.getValue());
	}
	
	public boolean isMinReached() {
		return (players.size() > 1);
	}
	
	public boolean isMaxReached() {
		return (players.size() == DAC.getDACConfig().getMaxPlayers());
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
		// Should never be reached;
		return colors.defaut();
	}

	public void addPlayer(Player player, String[] names) {
		int i = 0;
		DACColor color;
		while (i < names.length && !isColorAvailable(names[i])) { i++; }
		if (i == names.length) {
			color = getFirstColorAvailable();
		} else {
			color = colors.get(names[i]);
		}
		addPlayer(player, color);
	}

	private void addPlayer(Player player, DACColor color) {
		if (players.size() > 0) {
			player.sendMessage(DACMessage.JoinCurrentPlayers.getValue());
			for (DACPlayer dacPlayer : players) {
				player.sendMessage(DACMessage.JoinPlayerList.format(dacPlayer.getDisplayName()));
			}
		}
		DACPlayer dacPlayer = new DACPlayer(player, color);
		players.add(dacPlayer);
		colorsMap.add(color);
		notify(DACMessage.JoinPlayerJoin.format(dacPlayer.getDisplayName()));
	}

	public boolean contains(Player player) {
		for (DACPlayer dacPlayer : players) {
			if (dacPlayer.getPlayer().equals(player)) {
				return true;
			}
		}
		return false;
	}

	public void remove(Player player) {
		for (DACPlayer dacPlayer : players) {
			if (dacPlayer.getPlayer().equals(player)) {
				notify(DACMessage.JoinPlayerQuit.format(dacPlayer.getDisplayName()));				
				players.remove(dacPlayer);
				colorsMap.remove(dacPlayer.getColor());
				return;
			}
		}
	}

	public void stop() {
		for (DACPlayer player : players) {
			player.getPlayer().sendMessage(DACMessage.JoinStopped.toString());
		}
	}

}
