package fr.aumgn.dac.api;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.arena.Arenas;
import fr.aumgn.dac.api.config.DACColors;
import fr.aumgn.dac.api.config.DACConfig;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.fillstrategy.FillStrategies;
import fr.aumgn.dac.api.game.mode.GameModes;
import fr.aumgn.dac.api.stage.StageManager;
import fr.aumgn.dac.api.stage.StagePlayerManager;
import fr.aumgn.dac.plugin.DACPlugin;

/**
 * Core static class which offers access to all main components
 */
public final class DAC {

    private static Plugin plugin = null;
    private static WorldEditPlugin worldEdit = null;
    private static Listener listener = null;
    private static GameModes gameModes;
    private static Arenas arenas;
    private static FillStrategies fillStrategies;
    private static Random rand;
    private static StageManager stageManager;
    private static StagePlayerManager playerManager;
    private static DACConfig config;
    private static DACColors colors;

    private DAC() {}

    /**
     * Initialize DAC class
     * <p/>
     * This method is called by the main plugin in order to initialize components
     * and will raise an exception if called a second time. 
     * 
     * @throws UnsupportedOperationException if already initialized
     */
    public static void init(Plugin plugin, WorldEditPlugin worldEdit, Listener listener,
            GameModes gameModes, Arenas arenas) {
        if (DAC.plugin != null) {
            throw new UnsupportedOperationException("Cannot init DAC twice.");
        }
        DAC.plugin = plugin;
        DAC.worldEdit = worldEdit;
        DAC.listener = listener;
        DAC.gameModes = gameModes;
        DAC.arenas = arenas;
        DAC.fillStrategies = new FillStrategies();
        rand = new Random();
        stageManager = new StageManager();
        playerManager = new StagePlayerManager();
        reloadConfig();
        reloadColors();
        reloadMessages();
        arenas.load();
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
        config = ((DACPlugin) plugin).reloadDACConfig();
        // Update cached safe regions.
        for (Arena arena : getArenas()) {
            arena.getPool().updateSafeRegion();
        }
    }

    public static void reloadColors() {
        colors = ((DACPlugin) plugin).reloadDACColors();
    }

    public static void reloadMessages() {
        DACMessage.reload(plugin);
    }

    public static DACConfig getConfig() {
        return config;
    }

    public static DACColors getColors() {
        return colors;
    }

    public static GameModes getModes() {
        return gameModes;
    }

    public static Arenas getArenas() {
        return arenas;
    }

    public static FillStrategies getFillStrategies() {
        return fillStrategies;
    }

    public static StageManager getStageManager() {
        return stageManager;
    }

    public static StagePlayerManager getPlayerManager() {
        return playerManager;
    }

    public static WorldEditPlugin getWorldEdit() {
        return worldEdit;
    }

    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public static void registerListener() {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    public static void unregisterListener() {
        HandlerList.unregisterAll(plugin);
    }

}
