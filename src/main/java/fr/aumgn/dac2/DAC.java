package fr.aumgn.dac2;

import fr.aumgn.bukkitutils.localization.PluginResourceBundles;
import fr.aumgn.bukkitutils.localization.bundle.PluginResourceBundle;
import fr.aumgn.dac2.config.DACConfig;

public class DAC {

    private final DACPlugin plugin;

    private DACConfig config;
    private PluginResourceBundle cmdMessages;
    private PluginResourceBundle messages;

    public DAC(DACPlugin plugin) {
        this.plugin = plugin;
        reloadData();
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

    public void reloadData() {
        config = plugin.reloadDACConfig();
        PluginResourceBundles bundles = new PluginResourceBundles(plugin,
                config.getLocale(), plugin.getDataFolder());
        cmdMessages = bundles.get("commands");
        messages = bundles.get("messages");
    }
}
