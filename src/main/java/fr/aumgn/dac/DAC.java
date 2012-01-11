package fr.aumgn.dac;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.arenas.DACArenas;
import fr.aumgn.dac.command.DACCommand;

public class DAC extends JavaPlugin {
	
	public static class DACWorldEditNotLoaded extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public DACWorldEditNotLoaded() {
			super("Fail ! WorldEdit is not loaded !");
		}
	}

	private static final Logger logger = Logger.getLogger("Minecraft.DAC");
	
	private DACArenas config; 
	private Map<String, DACJoinStep> joinSteps;
	private Map<String, DACGame> games;
	private WorldEditPlugin worldEdit;
	
	private final EntityListener entityListener = new EntityListener() {
		
		public void onEntityDamage(EntityDamageEvent event) {
			DamageCause cause = event.getCause();
			if (event.getEntity() instanceof Player && cause == DamageCause.FALL) {
				DACGame game = getGame((Player)event.getEntity());
				if (game != null) { game.onPlayerDamage(event); }
			}
		}
		
	};
	
	private final PlayerListener playerListener = new PlayerListener() {
		
		public void onPlayerMove(PlayerMoveEvent event) {
			DACGame game = getGame(event.getPlayer());
			if (game != null) { game.onPlayerMove(event); }
		}
		
		public void onPlayerQuit(PlayerQuitEvent event) {
			Player player = event.getPlayer();
			DACJoinStep joinStep = getJoinStep(player);
			if (joinStep != null) {
				joinStep.remove(player);
				return;
			}
			DACGame game = getGame(player);
			if (game != null) { game.onPlayerQuit(player); }
		}
		
	};
	
	public static Logger getLogger() {
		return logger;
	}

	@Override
	public void onEnable() {
		PluginManager pm = Bukkit.getPluginManager();

		config = new DACArenas(this);
		joinSteps = new HashMap<String, DACJoinStep>();
		games = new HashMap<String, DACGame>();
		
		Plugin plugin = pm.getPlugin("WorldEdit");
	    if (!(plugin instanceof WorldEditPlugin)) {
	    	throw new DACWorldEditNotLoaded();
	    } else {
	    	worldEdit = (WorldEditPlugin)plugin;
	    }
	    
	    DACCommand dacCommand = new DACCommand(this);
	    Bukkit.getPluginCommand("dac").setExecutor(dacCommand);
		
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.High, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Monitor, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Monitor, this);
		
		logger.info(getDescription().getFullName() + " is enabled.");
	}
	
	@Override
	public void onDisable() {
		config.dump();
		logger.info(getDescription().getFullName() + " is disabled.");
	}
	
	public DACArenas getArenas() {
		return config;
	}

	public DACJoinStep getJoinStep(DACArena arena) {
		return joinSteps.get(arena.getName());
	}
	
	public DACJoinStep getJoinStep(Player player) {
		for (DACJoinStep joinStep : joinSteps.values()) {
			if (joinStep.contains(player)) { return joinStep; }
		}
		return null;
	}

	public void setJoinStep(DACJoinStep joinStep) {
		joinSteps.put(joinStep.getArena().getName(), joinStep);
	}

	public DACGame getGame(DACArena arena) {
		return games.get(arena.getName());
	}
	
	public DACGame getGame(Player player) {
		for (DACGame game : games.values()) {
			if (game.contains(player)) { return game; }
		}
		return null;
	}

	public void setGame(DACGame game) {
		games.put(game.getArena().getName(), game);
	}

	public boolean isPlayerInGame(Player player) {
		for (DACJoinStep joinStep : joinSteps.values()) {
			if (joinStep.contains(player)) { return true; }
		}
		for (DACGame game : games.values()) {
			if (game.contains(player)) { return true; }
		}
		return false;
	}

	public void removeJoinStep(DACJoinStep joinStep) {
		joinSteps.remove(joinStep.getArena().getName());
	}
	
	public void removeGame(DACGame game) {
		games.remove(game.getArena().getName());
	}

	public WorldEditPlugin getWorldEdit() {
		return worldEdit;
	}
	
}
