package fr.aumgn.dac2;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.aumgn.bukkitutils.localization.Localization;
import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.dac2.arena.Arenas;
import fr.aumgn.dac2.config.DACConfig;
import fr.aumgn.dac2.exceptions.WorldEditNotAvailable;
import fr.aumgn.dac2.stage.Stages;

public class DAC {

    private final DACPlugin plugin;

    private DACConfig config;
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

    public DACPlugin getPlugin() {
        return plugin;
    }

    public DACConfig getConfig() {
        return config;
    }

    public PluginMessages getCmdMessages() {
        return cmdMessages;
    }

    public PluginMessages getMessages() {
        return messages;
    }

    public void reloadData() {
        config = plugin.reloadDACConfig();

        Localization localization =
                new Localization(plugin, config.getLocale());
        cmdMessages = localization.get("commands");
        messages = localization.get("messages");
    }

    public WorldEditPlugin getWorldEdit() {
        Plugin worldEdit = Bukkit.getPluginManager().getPlugin("WorldEdit");
        if (!(worldEdit instanceof WorldEditPlugin)) {
            throw new WorldEditNotAvailable(this);
        }

        return (WorldEditPlugin) worldEdit;
    }

    public Arenas getArenas() {
        return arenas;
    }

    public Stages getStages() {
        return stages;
    }
}
