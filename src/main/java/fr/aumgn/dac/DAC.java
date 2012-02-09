package fr.aumgn.dac;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.dac.arenas.DACArenas;
import fr.aumgn.dac.bukkit.DACPlugin;
import fr.aumgn.dac.config.DACConfig;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.stage.StageManager;

public final class DAC {

	private static DACPlugin plugin = null;
	private static WorldEditPlugin worldEdit = null;
	private static DACConfig config;
	private static DACArenas arenas; 
	private static StageManager stageManager;

	private DAC() {}

	public static void init(DACPlugin plugin, WorldEditPlugin worldEdit) {
		if (DAC.plugin != null) {
			throw new UnsupportedOperationException("Cannot init DAC twice.");
		}
		DAC.plugin = plugin;
		DAC.worldEdit = worldEdit;
		arenas = new DACArenas();
		stageManager = new StageManager();
		reloadConfig();
		reloadMessages();
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static Logger getLogger() {
		return plugin.getLogger();
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

	public static DACArenas getArenas() {
		return arenas;
	}
	
	public static StageManager getStageManager() {
		return stageManager;
	}
	
	public static WorldEditPlugin getWorldEdit() {
		return worldEdit;
	}

}
