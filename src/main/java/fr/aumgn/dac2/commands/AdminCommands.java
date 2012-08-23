package fr.aumgn.dac2.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.dac2.DAC;

@NestedCommands("dac2")
public class AdminCommands extends DACCommands {

    public AdminCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "version")
    public void version(CommandSender sender) {
        PluginDescriptionFile desc = dac.getPlugin().getDescription();
        sender.sendMessage(msg("version", desc.getVersion()));
    }

    @Command(name = "reload")
    public void reload(CommandSender sender) {
        dac.reloadData();
        sender.sendMessage(msg("reload.success"));
    }
}
