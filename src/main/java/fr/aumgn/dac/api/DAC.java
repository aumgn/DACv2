package fr.aumgn.dac.api;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.dac.api.arena.Arenas;
import fr.aumgn.dac.api.config.DACConfig;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.mode.DACGameModes;
import fr.aumgn.dac.api.stage.StageManager;
import fr.aumgn.dac.api.stage.StagePlayersManager;

public final class DAC {

	private static Plugin plugin = null;
	private static WorldEditPlugin worldEdit = null;
	private static Random rand;
	private static DACGameModes gameModes; 
	private static Arenas arenas; 
	private static StageManager stageManager;
	private static StagePlayersManager playerManager;
	private static DACConfig config;

	private DAC() {}

	public static void init(Plugin plugin, WorldEditPlugin worldEdit, Arenas arenas) {
		if (DAC.plugin != null) {
			throw new UnsupportedOperationException("Cannot init DAC twice.");
		}
		DAC.plugin = plugin;
		DAC.worldEdit = worldEdit;
		DAC.arenas = arenas;
		rand = new Random();
		gameModes = new DACGameModes();
		stageManager = new StageManager();
		playerManager = new StagePlayersManager();
		reloadConfig();
		reloadMessages();
	}

	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static DACCommand getCommand() {
		PluginCommand plCmd = Bukkit.getPluginCommand("dac");
		if (plCmd.getExecutor() instanceof DACCommand) {
			return (DACCommand) plCmd.getExecutor();
		}
		return null;
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

	public static Arenas getArenas() {
		return arenas;
	}
	
	public static StageManager getStageManager() {
		return stageManager;
	}
	
	public static StagePlayersManager getPlayerManager() {
		return playerManager;
	}
	
	public static WorldEditPlugin getWorldEdit() {
		return worldEdit;
	}

	public static void callEvent(Event event) {
		Bukkit.getPluginManager().callEvent(event);
	}
	
}
