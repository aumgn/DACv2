package fr.aumgn.dac2;

import java.util.logging.Logger;

import fr.aumgn.bukkitutils.localization.Localization;
import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.dac2.arena.Arenas;
import fr.aumgn.dac2.config.Colors;
import fr.aumgn.dac2.config.DACConfig;
import fr.aumgn.dac2.stage.Stages;

/**
 * Main class which offers acces to all components.
 */
public class DAC {

    private final DACPlugin plugin;

    private DACConfig config;
    private Colors colors;
    private PluginMessages cmdMessages;
    private PluginMessages messages;

    private Arenas arenas;
    private Stages stages;

    public DAC(DACPlugin plugin) {
        this.plugin = plugin;
        reloadData();
        this.arenas = new Arenas(this);
        this.stages = new Stages(this);
    }

    public Logger getLogger() {
        return plugin.getLogger();
    }

    public DACPlugin getPlugin() {
        return plugin;
    }

    public DACConfig getConfig() {
        return config;
    }

    public Colors getColors() {
        return colors;
    }

    public PluginMessages getCmdMessages() {
        return cmdMessages;
    }

    public PluginMessages getMessages() {
        return messages;
    }

    /*
     * Reloads all datas.
     *
     * <ul>
     *  <li>Config</li>
     *  <li>Colors</li>
     *  <li>Messages</li>
     * </ul>
     */
    public void reloadData() {
        config = plugin.reloadDACConfig();
        colors = new Colors(this);

        Localization localization =
                new Localization(plugin, config.getLocale());
        cmdMessages = localization.get("commands");
        messages = localization.get("messages");
    }

    public Arenas getArenas() {
        return arenas;
    }

    public Stages getStages() {
        return stages;
    }
}
