package fr.aumgn.dac2.commands;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.regions.PoolFilling;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.shape.column.ColumnPattern;
import fr.aumgn.dac2.shape.column.RandomPattern;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@NestedCommands(value = { "dac2", "fill" }, defaultTo = "reset")
public class FillCommands extends DACCommands {

    public FillCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "reset", min = 1, max = -1)
    public void reset(CommandSender sender, CommandArgs args) {
        fillCommand(sender, args, new PoolFilling.Reset());
        sender.sendMessage(msg("fill.reset.success"));
    }

    @Command(name = "fully", min = 1, max = -1)
    public void fully(CommandSender sender, CommandArgs args) {
        fillCommand(sender, args, new PoolFilling.Fully());
        sender.sendMessage(msg("fill.fully.success"));
    }

    @Command(name = "dac", min = 1, max = -1)
    public void dac(CommandSender sender, CommandArgs args) {
        fillCommand(sender, args, new PoolFilling.DeACoudre());
        sender.sendMessage(msg("fill.dac.success"));
    }

    @Command(name = "randomly", min = 1, max = -1, argsFlags = "p")
    public void randomly(CommandSender sender, CommandArgs args) {
        double ratio = ((double) args.getInteger('p').valueOr(50)) / 100;
        fillCommand(sender, args, new PoolFilling.Randomly(ratio));
        sender.sendMessage(msg("fill.randomly.success"));
    }

    @Command(name = "allbutone", min = 1, max = -1)
    public void allButOne(CommandSender sender, CommandArgs args) {
        fillCommand(sender, args, new PoolFilling.AllButOne());
        sender.sendMessage(msg("fill.allbutone.success"));
    }

    private void fillCommand(CommandSender sender, CommandArgs args,
                             PoolFilling strategy) {
        Arena arena = args.get(0, Arena).value();
        List<Color> colors;
        if (args.length() == 1) {
            colors = dac.getColors().asList();
        }
        else {
            colors = new ArrayList<Color>();
            for (int i = 1; i < args.length(); i++) {
                colors.add(args.get(i, Color).value());
            }
        }

        ColumnPattern pattern = RandomPattern.fromColors(colors);
        strategy.fill(arena.getWorld(), arena.safeGetPool(dac), pattern);
    }
}
