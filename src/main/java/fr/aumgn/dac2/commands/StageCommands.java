package fr.aumgn.dac2.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.stage.JoinStage;

@NestedCommands(name = "dac2")
public class StageCommands extends DACCommands {

    public StageCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "initialize", min = 0, max = 1)
    public void init(CommandSender sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).valueOr(sender);

        JoinStage joinStage = new JoinStage(dac, arena);
        dac.getStages().start(joinStage);

        if (!(sender instanceof Player)) {
            sender.sendMessage(msg("init.success", arena.getName()));
        }
    }
}
