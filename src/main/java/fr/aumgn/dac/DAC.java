package fr.aumgn.dac;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.dac.arenas.DACArenas;
import fr.aumgn.dac.config.DACConfig;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.stage.StageManager;

public final class DAC {

	private static final String MESSAGES_FILENAME = "messages.yml";
	private static DACPlugin plugin = null;
	private static WorldEditPlugin worldEdit = null;
	private static DACConfig config;
	private static DACArenas arenas; 
	private static StageManager stageManager;
	private static Map<String, GameMode> modes;

	private DAC() {}

	public static void init(DACPlugin plugin, WorldEditPlugin worldEdit) {
		if (DAC.plugin != null) {
			throw new UnsupportedOperationException("Cannot init DAC twice.");
		}
		DAC.plugin = plugin;
		DAC.worldEdit = worldEdit;
		arenas = new DACArenas();
		stageManager = new StageManager();
		modes = new HashMap<String, GameMode>(); 
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
		YamlConfiguration newMessages = new YamlConfiguration();
		YamlConfiguration defaultMessages = new YamlConfiguration();
		try {
			newMessages.load(new File(plugin.getDataFolder(), MESSAGES_FILENAME));
			defaultMessages.load(plugin.getResource(MESSAGES_FILENAME));
			DACMessage.load(newMessages, defaultMessages);
		} catch (IOException exc) {
			getLogger().severe("Unable to read " + MESSAGES_FILENAME + " file.");
			getLogger().severe(exc.getClass().getSimpleName() + " exception raised");
		} catch (InvalidConfigurationException exc) {
			getLogger().severe("Unable to load " + MESSAGES_FILENAME + " file.");
			getLogger().severe(exc.getClass().getSimpleName() + " exception raised");
		}
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
	
	public static GameMode getMode(String name) {
		return modes.get(name);
	}
	
	public static WorldEditPlugin getWorldEdit() {
		return worldEdit;
	}

}
