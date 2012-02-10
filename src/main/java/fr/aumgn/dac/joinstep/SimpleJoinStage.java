package fr.aumgn.dac.joinstep;

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
import fr.aumgn.dac.game.mode.DACGameMode;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.player.DACPlayer;

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
		// Should never be reached;
		return colors.defaut();
	}
	
	private void addPlayer(Player player, DACColor color) {
		DACPlayer dacPlayer = new JoinStagePlayer(this, player, color);
		players.add(dacPlayer);
		DAC.getPlayerManager().register(dacPlayer);
		colorsMap.add(color);
		send(DACMessage.JoinPlayerJoin.format(dacPlayer.getDisplayName()));
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
		for (DACPlayer dacPlayer : players) {
			if (dacPlayer.getPlayer().equals(player)) {
				send(DACMessage.JoinPlayerQuit.format(dacPlayer.getDisplayName()));
				players.remove(dacPlayer);
				DAC.getPlayerManager().unregister(dacPlayer);
				colorsMap.remove(dacPlayer.getColor());
				return;
			}
		}
	}
	
	@Override
	public List<DACPlayer> getPlayers() {
		return players;
	}

	@Override
	public void stop() {
		send(DACMessage.JoinStopped);
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
