package fr.aumgn.dac.plugin.command;

import org.bukkit.command.CommandSender;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACMessage;

@NestedCommands(name = "dac")
public class AdminCommands extends DACCommands {

    @Command(name = "reload")
    public void reload(CommandSender sender, CommandArgs args) {
        DAC.reloadConfig();
        DAC.reloadMessages();
        success(sender, DACMessage.CmdReloadSuccess);
    }
}
