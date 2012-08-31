package fr.aumgn.dac2.commands.worldedit;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.FlatRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.arena.regions.StartRegion;
import fr.aumgn.dac2.arena.regions.SurroundingRegion;
import fr.aumgn.dac2.exceptions.PoolShapeNotFlat;
import fr.aumgn.dac2.exceptions.WERegionIncomplete;
import fr.aumgn.dac2.shape.FlatShape;
import fr.aumgn.dac2.shape.Shape;
import fr.aumgn.dac2.shape.worldedit.WEShapeUtils;

@NestedCommands({"dac2", "setwe"})
public class SetupWECommands extends WorldEditCommands {

    public SetupWECommands(DAC dac) {
        super(dac);
    }

    @Command(name = "pool", min = 1, max = 1)
    public void pool(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        Region region = getRegion(sender);
        if (!(region instanceof FlatRegion)) {
            throw new PoolShapeNotFlat(dac, region);
        }

        FlatShape shape = (FlatShape) WEShapeUtils.getShape(dac, region);
        Pool pool = new Pool(shape);
        arena.setPool(pool);
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("setwe.pool.success"));
    }

    @Command(name = "start", min = 1, max = 1)
    public void start(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        Region region = getRegion(sender);

        Shape shape = WEShapeUtils.getShape(dac, region);
        StartRegion start = new StartRegion(shape);
        arena.setStartRegion(start);
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("setwe.start.success"));
    }

    @Command(name = "surrounding", min = 1, max = 1)
    public void surrounding(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        Region region = getRegion(sender);

        Shape shape = WEShapeUtils.getShape(dac, region);
        SurroundingRegion surrounding = new SurroundingRegion(shape);
        arena.setSurroundingRegion(surrounding);
        dac.getArenas().saveArena(dac, arena);
        sender.sendMessage(msg("setwe.surrounding.success"));
    }

    private Region getRegion(Player player) {
        WorldEditPlugin worldEdit = getWorldEdit();
        LocalWorld world = BukkitUtil.getLocalWorld(player.getWorld());
        RegionSelector selector = worldEdit.getSession(player)
                .getRegionSelector(world);
        try {
            return selector.getRegion();
        } catch (IncompleteRegionException exc) {
            throw new WERegionIncomplete(dac);
        }
    }
}
