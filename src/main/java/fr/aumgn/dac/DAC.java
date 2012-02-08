package fr.aumgn.dac;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.arenas.DACArenas;
import fr.aumgn.dac.config.DACConfig;
import fr.aumgn.dac.config.DACMessage;

public final class DAC {

	private static final String MESSAGES_FILENAME = "messages.yml";
	private static DACPlugin plugin = null;
	private static WorldEditPlugin worldEdit = null;
	private static DACConfig config;
	private static DACArenas arenas; 
	private static Map<String, DACJoinStep> joinSteps;
	private static Map<String, DACGame> games;
	
	private DAC() {}

	public static void init(DACPlugin plugin, WorldEditPlugin worldEdit) {
		if (DAC.plugin != null) {
			throw new UnsupportedOperationException("Cannot init DAC twice.");
		}
		DAC.plugin = plugin;
		DAC.worldEdit = worldEdit;
		arenas = new DACArenas();
		joinSteps = new HashMap<String, DACJoinStep>();
		games = new HashMap<String, DACGame>();
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
	
	public static DACJoinStep getJoinStep(DACArena arena) {
		return joinSteps.get(arena.getName());
	}
	
	public static DACJoinStep getJoinStep(Player player) {
		for (DACJoinStep joinStep : joinSteps.values()) {
			if (joinStep.contains(player)) { return joinStep; }
		}
		return null;
	}

	public static void addJoinStep(DACJoinStep joinStep) {
		joinSteps.put(joinStep.getArena().getName(), joinStep);
	}

	public static DACGame getGame(DACArena arena) {
		return games.get(arena.getName());
	}

	public static DACGame getGame(Player player) {
		for (DACGame game : games.values()) {
			if (game.contains(player)) { return game; }
		}
		return null;
	}

	public static void addGame(DACGame game) {
		games.put(game.getArena().getName(), game);
	}

	public static boolean isPlayerInGame(Player player) {
		for (DACJoinStep joinStep : joinSteps.values()) {
			if (joinStep.contains(player)) { return true; }
		}
		for (DACGame game : games.values()) {
			if (game.contains(player)) { return true; }
		}
		return false;
	}

	public static void removeJoinStep(DACJoinStep joinStep) {
		joinSteps.remove(joinStep.getArena().getName());
	}

	public static void removeGame(DACGame game) {
		games.remove(game.getArena().getName());
	}

	public static WorldEditPlugin getWorldEdit() {
		return worldEdit;
	}

}
