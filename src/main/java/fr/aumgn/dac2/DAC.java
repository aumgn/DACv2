package fr.aumgn.dac2;

import fr.aumgn.bukkitutils.localization.PluginResourceBundles;
import fr.aumgn.bukkitutils.localization.bundle.PluginResourceBundle;
import fr.aumgn.dac2.config.DACConfig;

public class DAC {

    private final DACPlugin plugin;
    private final DACConfig config;
    private final PluginResourceBundle cmdMessages;
    private final PluginResourceBundle messages;

    public DAC(DACPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.reloadDACConfig();
        PluginResourceBundles bundles = new PluginResourceBundles(plugin,
                config.getLocale(), plugin.getDataFolder());
        this.cmdMessages = bundles.get("commands");
        this.messages = bundles.get("messages");
    }

    public DACPlugin getPlugin() {
        return plugin;
    }

    public DACConfig getConfig() {
        return config;
    }

    public PluginResourceBundle getCmdMessages() {
        return cmdMessages;
    }

    public PluginResourceBundle getMessages() {
        return messages;
    }
}
