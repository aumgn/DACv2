package fr.aumgn.dac.plugin.command;

import java.util.Map;

import org.bukkit.command.CommandSender;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.exception.DACException;

@NestedCommands(name = {"dac", "options"})
public class OptionsCommands extends DACCommands {

    @Command(name = "list", min = 1, max = 1)
    public void list(CommandSender sender, CommandArgs args) {
        Arena arena = DAC.getArenas().get(args.get(0));
        if (arena == null) {
            throw new DACException(DACMessage.CmdOptionsUnknown);
        }

        sender.sendMessage(DACMessage.CmdOptionsList.getContent());
        for (Map.Entry<String, String> option : arena.optionsEntries()) {
            sender.sendMessage(DACMessage.CmdOptionsOption.getContent(
                    option.getKey(), option.getValue()));
        }
    }

    @Command(name = "set", min = 2, max = -1)
    public void set(CommandSender sender, CommandArgs args) {
        Arena arena = DAC.getArenas().get(args.get(0));
        if (arena == null) {
            throw new DACException(DACMessage.CmdOptionsUnknown);
        }

        for (String arg : args.asList(1)) {
            String[] option = arg.split(":");
            if (option.length == 2) {
                arena.setOption(option[0], option[1]);
            }
        }
        success(sender, DACMessage.CmdOptionsAddSuccess);
    }

    @Command(name = "remove", min = 2, max = -1)
    public void remove(CommandSender sender, CommandArgs args) {
        Arena arena = DAC.getArenas().get(args.get(0));
        if (arena == null) {
            throw new DACException(DACMessage.CmdOptionsUnknown);
        }

        for (String arg : args.asList(1)) {
            arena.removeOption(arg);
        }
        success(sender, DACMessage.CmdOptionsRemoveSuccess);
    }
}
