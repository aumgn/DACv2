package fr.aumgn.dac;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.arenas.DACArenas;
import fr.aumgn.dac.command.DACCommand;
import fr.aumgn.dac.config.DACConfig;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.listener.DACEntityListener;
import fr.aumgn.dac.listener.DACPlayerListener;

public class DAC extends JavaPlugin {
	
	private static final String MessagesFile = "messages.yml";
	private static final Logger logger = Logger.getLogger("Minecraft.DAC");
	private static DAC plugin;
	
	public static DAC getPlugin() {
		return plugin;
	}
	
	public static Logger getLogger() {
		return logger;
	}

	public static void reloadDACConfig() {
		plugin.reloadConfig();
		plugin.config = new DACConfig(plugin.getConfig());
	}	
	
	public static void reloadMessages() {
		YamlConfiguration newMessages = new YamlConfiguration();
		YamlConfiguration defaultMessages = new YamlConfiguration();
		try {
			newMessages.load(new File(plugin.getDataFolder(), MessagesFile));
			defaultMessages.load(plugin.getResource(MessagesFile));
			DACMessage.load(newMessages, defaultMessages);
		} catch (IOException exc) {
			getLogger().severe("Unable to read " + MessagesFile + " file.");
			getLogger().severe(exc.getClass().getSimpleName() + " exception raised");
		} catch (InvalidConfigurationException exc) {
			getLogger().severe("Unable to load " + MessagesFile + " file.");
			getLogger().severe(exc.getClass().getSimpleName() + " exception raised");
		}
	}
	
	public static DACConfig getDACConfig() {
		return plugin.config;
	}
	
	public static DACArenas getArenas() {
		return plugin.arenas;
	}

	public static DACJoinStep getJoinStep(DACArena arena) {
		return plugin.joinSteps.get(arena.getName());
	}
	
	public static DACJoinStep getJoinStep(Player player) {
		for (DACJoinStep joinStep : plugin.joinSteps.values()) {
			if (joinStep.contains(player)) { return joinStep; }
		}
		return null;
	}

	public static void setJoinStep(DACJoinStep joinStep) {
		plugin.joinSteps.put(joinStep.getArena().getName(), joinStep);
	}

	public static DACGame getGame(DACArena arena) {
		return plugin.games.get(arena.getName());
	}
	
	public static DACGame getGame(Player player) {
		for (DACGame game : plugin.games.values()) {
			if (game.contains(player)) { return game; }
		}
		return null;
	}

	public static void setGame(DACGame game) {
		plugin.games.put(game.getArena().getName(), game);
	}

	public static boolean isPlayerInGame(Player player) {
		for (DACJoinStep joinStep : plugin.joinSteps.values()) {
			if (joinStep.contains(player)) { return true; }
		}
		for (DACGame game : plugin.games.values()) {
			if (game.contains(player)) { return true; }
		}
		return false;
	}

	public static void removeJoinStep(DACJoinStep joinStep) {
		plugin.joinSteps.remove(joinStep.getArena().getName());
	}
	
	public static void removeGame(DACGame game) {
		plugin.games.remove(game.getArena().getName());
	}

	public static WorldEditPlugin getWorldEdit() {
		return plugin.worldEdit;
	}
	
	private DACConfig config;
	private DACArenas arenas; 
	private Map<String, DACJoinStep> joinSteps;
	private Map<String, DACGame> games;
	private WorldEditPlugin worldEdit;
	
	@Override
	public void onEnable() {
		if (DAC.plugin != null) {
			throw new UnsupportedOperationException("DAC seems to have been loaded twice.");
		}
		DAC.plugin = this;
				
		PluginManager pm = Bukkit.getPluginManager();

		arenas = new DACArenas(this);
		joinSteps = new HashMap<String, DACJoinStep>();
		games = new HashMap<String, DACGame>();
		
		Plugin plugin = pm.getPlugin("WorldEdit");
	    if (!(plugin instanceof WorldEditPlugin)) {
	    	throw new DACException.WorldEditNotLoaded();
	    } else {
	    	worldEdit = (WorldEditPlugin)plugin;
	    }
	    
	    if (!new File(getDataFolder(), "config.yml").exists()) {
	    	saveDefaultConfig();
	    }
	    if (!new File(getDataFolder(), "messages.yml").exists()) {
	    	saveResource("messages.yml", false);
	    }
	    DAC.reloadDACConfig();
	    DAC.reloadMessages();
	    
	    DACCommand dacCommand = new DACCommand();
	    Bukkit.getPluginCommand("dac").setExecutor(dacCommand);
		
	    DACEntityListener entityListener = new DACEntityListener();
	    DACPlayerListener playerListener = new DACPlayerListener();
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.High, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Monitor, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Monitor, this);
		
		logger.info(getDescription().getFullName() + " is enabled.");
	}
	
	@Override
	public void onDisable() {
		arenas.dump();
		DAC.plugin = null;
		logger.info(getDescription().getFullName() + " is disabled.");
	}

}
