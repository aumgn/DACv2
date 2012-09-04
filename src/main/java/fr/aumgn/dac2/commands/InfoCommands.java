package fr.aumgn.dac2.commands;

import org.bukkit.command.CommandSender;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.stage.Stage;

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
}