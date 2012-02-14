package fr.aumgn.dac;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.dac.arena.DACArenas;
import fr.aumgn.dac.bukkit.DACPlugin;
import fr.aumgn.dac.config.DACConfig;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.game.mode.DACGameModes;
import fr.aumgn.dac.player.DACPlayerManager;
import fr.aumgn.dac.stage.StageManager;

public final class DAC {

	private static DACPlugin plugin = null;
	private static WorldEditPlugin worldEdit = null;
	private static Random rand;
	private static DACGameModes gameModes; 
	private static DACArenas arenas; 
	private static StageManager stageManager;
	private static DACPlayerManager playerManager;
	private static DACConfig config;

	private DAC() {}

	public static void init(DACPlugin plugin, WorldEditPlugin worldEdit) {
		if (DAC.plugin != null) {
			throw new UnsupportedOperationException("Cannot init DAC twice.");
		}
		DAC.plugin = plugin;
		DAC.worldEdit = worldEdit;
		rand = new Random();
		gameModes = new DACGameModes(); 
		arenas = new DACArenas();
		stageManager = new StageManager();
		playerManager = new DACPlayerManager();
		reloadConfig();
		reloadMessages();
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static Logger getLogger() {
		return plugin.getLogger();
	}
	
	public static Random getRand() {
		return rand;
	}

	public static void reloadConfig() {
		plugin.reloadConfig();
		config = new DACConfig(plugin.getConfig());
	}	

	public static void reloadMessages() {
		DACMessage.reload(plugin);
	}

	public static DACConfig getConfig() {
		return config;
	}
	
	public static DACGameModes getModes() {
		return gameModes;
	}

	public static DACArenas getArenas() {
		return arenas;
	}
	
	public static StageManager getStageManager() {
		return stageManager;
	}
	
	public static DACPlayerManager getPlayerManager() {
		return playerManager;
	}
	
	public static WorldEditPlugin getWorldEdit() {
		return worldEdit;
	}

	public static void callEvent(Event event) {
		Bukkit.getPluginManager().callEvent(event);
	}
	
}
