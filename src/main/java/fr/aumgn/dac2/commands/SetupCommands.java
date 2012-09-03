package fr.aumgn.dac2.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
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
import fr.aumgn.dac2.arena.regions.SurroundingRegion;
import fr.aumgn.dac2.exceptions.PoolShapeNotFlat;
import fr.aumgn.dac2.shape.FlatShape;
import fr.aumgn.dac2.shape.Shape;
import fr.aumgn.dac2.shape.ShapeFactory;

@NestedCommands({"dac2", "set"})
public class SetupCommands extends DACCommands {

    private static final int DEFAULT_RADIUS = 5;
    private static final int DEFAULT_HEIGHT = 3;

    public SetupCommands(DAC dac) {
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
        int radius = args.getInteger(1).valueOr(DEFAULT_RADIUS);
        int height = args.getInteger(2).valueOr(DEFAULT_HEIGHT);
        Player player = args.getPlayer('p').valueOr(sender);
        ShapeFactory factory = args.get('s', ShapeFactory.class)
                .valueOr(ShapeFactory.Arbitrary);

        Shape shape = factory.create(dac, player.getWorld(),
                centerFrom(player), radius, height);
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
        int radius = args.getInteger(1).valueOr(DEFAULT_RADIUS);
        int height = args.getInteger(2).valueOr(DEFAULT_HEIGHT);
        Player player = args.getPlayer('p').valueOr(sender);
        ShapeFactory factory = args.get('s', ShapeFactory.class)
                .valueOr(ShapeFactory.Cuboid);

        Shape shape = factory.create(dac, player.getWorld(),
                centerFrom(player), radius, height);
        arena.setStartRegion(new StartRegion(shape));
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("set.diving.success"));
    }

    @Command(name = "surrounding", min = 1, max = 3, argsFlags = "sp")
    public void surrounding(CommandSender sender, CommandArgs args) {
        Arena arena = args.get(0, Arena).value();
        Player player = args.getPlayer('p').valueOr(sender);
        int radius = args.getInteger(1).valueOr(DEFAULT_RADIUS);
        int height = args.getInteger(2).valueOr(DEFAULT_HEIGHT);
        ShapeFactory factory = args.get('s', ShapeFactory.class)
                .valueOr(ShapeFactory.Cuboid);

        Shape shape = factory.create(dac, player.getWorld(),
                centerFrom(player), radius, height);
        arena.setSurroundingRegion(new SurroundingRegion(shape));
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("set.diving.success"));
    }

    private Vector centerFrom(Player player) {
        Location loc = player.getLocation();
        return new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
}
