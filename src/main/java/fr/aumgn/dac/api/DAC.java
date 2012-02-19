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
import fr.aumgn.dac.api.fillstrategy.FillStrategies;
import fr.aumgn.dac.api.game.mode.GameModes;
import fr.aumgn.dac.api.stage.StageManager;
import fr.aumgn.dac.api.stage.StagePlayerManager;

/**
 * Core static class which offers access to all main component
 */
public final class DAC {

    private static Plugin plugin = null;
    private static WorldEditPlugin worldEdit = null;
    private static GameModes gameModes;
    private static Arenas arenas;
    private static FillStrategies fillStrategies;
    private static Random rand;
    private static StageManager stageManager;
    private static StagePlayerManager playerManager;
    private static DACConfig config;

    private DAC() {}

    /**
     * Init DAC class
     * <p/>
     * This method is called by the main plugin in order to initialize components
     * and will raise an exception if called a second time. 
     */
    public static void init(Plugin plugin, WorldEditPlugin worldEdit, DACConfig config, GameModes gameModes, Arenas arenas, FillStrategies fillStrategies) {
        if (DAC.plugin != null) {
            throw new UnsupportedOperationException("Cannot init DAC twice.");
        }
        DAC.plugin = plugin;
        DAC.worldEdit = worldEdit;
        DAC.gameModes = gameModes;
        DAC.arenas = arenas;
        DAC.fillStrategies = fillStrategies;
        rand = new Random();
        stageManager = new StageManager();
        playerManager = new StagePlayerManager();
        DAC.config = config;
        reloadConfig();
        reloadMessages();
        arenas.load();
    }

    /**
     * Gets the main DAC plugin instance.
     * 
     * @return DAC plugin instance
     */
    public static Plugin getPlugin() {
        return plugin;
    }

    /**
     * @see DACCommand
     */
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

    /**
     * Reloads DAC configuration.
     */
    public static void reloadConfig() {
        plugin.reloadConfig();
        config.load(plugin.getConfig());
    }

    /**
     * Reloads DAC messages.
     */
    public static void reloadMessages() {
        DACMessage.reload(plugin);
    }

    /**
     * @see DACConfig
     */
    public static DACConfig getConfig() {
        return config;
    }

    /**
     * @see GameModes
     */    
    public static GameModes getModes() {
        return gameModes;
    }

    /**
     * @see Arenas
     */
    public static Arenas getArenas() {
        return arenas;
    }

    /**
     * @see FillStrategies
     */
    public static FillStrategies getFillStrategies() {
        return fillStrategies;
    }

    /**
     * @see StageManager
     */
    public static StageManager getStageManager() {
        return stageManager;
    }

    /**
     * @see StagePlayerManager
     */
    public static StagePlayerManager getPlayerManager() {
        return playerManager;
    }

    public static WorldEditPlugin getWorldEdit() {
        return worldEdit;
    }

    /**
     * Calls an {@link Event} event.
     */
    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

}
