package fr.aumgn.dac2.commands.worldedit;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.commands.DACCommands;
import fr.aumgn.dac2.exceptions.WorldEditNotAvailable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class WorldEditCommands extends DACCommands {

    public WorldEditCommands(DAC dac) {
        super(dac);
    }

    public WorldEditPlugin getWorldEdit() {
        Plugin worldEdit = Bukkit.getPluginManager().getPlugin("WorldEdit");
        if (!(worldEdit instanceof WorldEditPlugin)) {
            throw new WorldEditNotAvailable(dac);
        }

        return (WorldEditPlugin) worldEdit;
    }
}
