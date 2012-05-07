package fr.aumgn.dac.plugin.command;

import org.bukkit.command.CommandSender;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.CommandArgs;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.exception.DACException;

@NestedCommands(name = {"dac", "modes"})
public class ModesCommands extends DACCommands {

    @Command(name = "list", min = 0, max = 1)
    public void list(CommandSender sender, CommandArgs args) {
        Iterable<String> modes;
        if (args.length() == 0) {
            modes = DAC.getModes().getNames();
        } else {
            Arena arena = DAC.getArenas().get(args.get(0));
            if (arena == null) {
                throw new DACException(DACMessage.CmdModesUnknown);
            }

            modes = arena.getAllowedModes();
        }

        sender.sendMessage(DACMessage.CmdModesList.getContent());
        for (String modeName : modes) {
            sender.sendMessage(DACMessage.CmdModesMode.getContent(modeName));
        }
    }

    @Command(name = "add", min = 2, max = 2)
    public void add(CommandSender sender, CommandArgs args) {
        Arena arena = DAC.getArenas().get(args.get(0));
        if (arena == null) {
            throw new DACException(DACMessage.CmdModesUnknown);
        }

        arena.addAllowedMode(args.get(1));
        success(sender, DACMessage.CmdModesAddSuccess);
    }

    @Command(name = "remove", min = 2, max = 2)
    public void remove(CommandSender sender, CommandArgs args) {
        Arena arena = DAC.getArenas().get(args.get(0));
        if (arena == null) {
            throw new DACException(DACMessage.CmdModesUnknown);
        }

        arena.removeAllowedMode(args.get(1));
        success(sender, DACMessage.CmdModesRemoveSuccess);
    }
}
