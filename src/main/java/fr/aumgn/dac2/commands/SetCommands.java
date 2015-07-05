package fr.aumgn.dac2.commands;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.Diving;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.arena.regions.StartRegion;
import fr.aumgn.dac2.arena.regions.SurroundingRegion;
import fr.aumgn.dac2.exceptions.PoolShapeNotFlat;
import fr.aumgn.dac2.shape.FlatShape;
import fr.aumgn.dac2.shape.Shape;
import fr.aumgn.dac2.shape.ShapeFactory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@NestedCommands({ "dac2", "set" })
public class SetCommands extends DACCommands {

    private static final int DEFAULT_RADIUS = 5;
    private static final int DEFAULT_HEIGHT = 3;

    public SetCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "diving", min = 1, max = 1, argsFlags = "p")
    public void diving(CommandSender sender, CommandArgs args) {
        Arena arena = args.get(0, Arena).value();
        Player player = args.getPlayer('p').valueOr(sender);

        arena.setDiving(new Diving(player.getLocation()));
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("set.diving.success"));
    }

    @Command(name = "pool", min = 1, max = 3, argsFlags = "sp")
    public void pool(CommandSender sender, CommandArgs args) {
        Arena arena = args.get(0, Arena).value();
        Shape shape = shapeFor(sender, args, ShapeFactory.Arbitrary);

        if (!(shape instanceof FlatShape)) {
            throw new PoolShapeNotFlat(dac, shape);
        }

        arena.setPool(new Pool((FlatShape) shape));
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("set.pool.success"));
    }

    @Command(name = "start", min = 1, max = 3, argsFlags = "sp")
    public void start(CommandSender sender, CommandArgs args) {
        Arena arena = args.get(0, Arena).value();
        Shape shape = shapeFor(sender, args, ShapeFactory.Cuboid);

        arena.setStartRegion(new StartRegion(shape));
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("set.start.success"));
    }

    @Command(name = "surrounding", min = 1, max = 3, argsFlags = "sp")
    public void surrounding(CommandSender sender, CommandArgs args) {
        Arena arena = args.get(0, Arena).value();
        Shape shape = shapeFor(sender, args, ShapeFactory.Cuboid);

        arena.setSurroundingRegion(new SurroundingRegion(shape));
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("set.surrounding.success"));
    }

    private Shape shapeFor(CommandSender sender, CommandArgs args,
                           ShapeFactory defaultShape) {
        Player player = args.getPlayer('p').valueOr(sender);
        Vector center = new Vector(player);
        int radius = args.getInteger(1).valueOr(DEFAULT_RADIUS);
        int height = args.getInteger(2).valueOr(DEFAULT_HEIGHT);
        ShapeFactory factory = args.get('s', ShapeFactory.class).valueOr(defaultShape);

        return factory.create(dac, player.getWorld(), center, radius, height);
    }
}
