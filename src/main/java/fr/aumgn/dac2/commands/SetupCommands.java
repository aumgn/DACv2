package fr.aumgn.dac2.commands;

import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.Diving;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.arena.regions.StartRegion;
import fr.aumgn.dac2.shape.ArbitraryFlatShapeVisitor;
import fr.aumgn.dac2.shape.CuboidShape;
import fr.aumgn.dac2.shape.Shape;

@NestedCommands({"dac2", "set"})
public class SetupCommands extends DACCommands {

    public SetupCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "diving", min = 1, max = 1)
    public void diving(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        arena.setDiving(new Diving(sender.getLocation()));
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("set.diving.success"));
    }

    @Command(name = "pool", min = 1, max = 1)
    public void pool(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        ArbitraryFlatShapeVisitor visitor = new ArbitraryFlatShapeVisitor(dac,
                sender.getWorld(), new Vector(sender));
        arena.setPool(new Pool(visitor.visit()));
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("set.pool.success"));
    }

    @Command(name = "start", min = 2, max = 2)
    public void start(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        int radius = args.getInteger(1).value();

        Vector pos = new Vector(sender);
        Shape shape = new CuboidShape(pos.subtract(radius), pos.add(radius));
        arena.setStartRegion(new StartRegion(shape));
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("set.diving.success"));
    }
}
