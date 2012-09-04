package fr.aumgn.dac2.commands;

import org.bukkit.command.CommandSender;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.stage.Stage;
import fr.aumgn.dac2.config.Color;

@NestedCommands("dac2")
public class InfoCommands extends DACCommands {

    public InfoCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "list", min = 0, max = 1)
    public void list(CommandSender sender, CommandArgs args) {
        Stage stage = args.get(0, Stage).valueOr(sender);
        stage.list(sender);
    }

    @Command(name = "colors")
    public void colors(CommandSender sender) {
        sender.sendMessage(msg("colors.head"));

        int i = 0;
        StringBuilder msg = new StringBuilder();
        for (Color color : dac.getColors()) {
            msg.append(color.chat + color.name).append(" ");
            if (i == 2) {
                sender.sendMessage(msg.toString());
                i = 0;
                msg = new StringBuilder();
            } else {
                i++;
            }
        }

        if (i != 0) {
            sender.sendMessage(msg.toString());
        }
    }
}