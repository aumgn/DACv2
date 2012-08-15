package fr.aumgn.dac2;

import fr.aumgn.dac2.config.DACConfig;

public class DAC {

    private final DACPlugin plugin;
    private final DACConfig config;

    public DAC(DACPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.reloadDACConfig();
    }

    public DACPlugin getPlugin() {
        return plugin;
    }

    public DACConfig getConfig() {
        return config;
    }
}
