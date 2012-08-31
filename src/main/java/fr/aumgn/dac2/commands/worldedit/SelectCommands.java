package fr.aumgn.dac2.commands.worldedit;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.RegionSelector;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.regions.Region;
import fr.aumgn.dac2.shape.Shape;
import fr.aumgn.dac2.shape.worldedit.WESelector;

@NestedCommands({ "dac2", "select" })
public class SelectCommands extends WorldEditCommands {

    public SelectCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "pool", min = 1, max = 1, argsFlags = "s")
    public void pool(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        WESelector selectorType = args.get('s', WESelector.class)
                .valueOr(WESelector.Default);

        setSelection(sender, arena, selectorType, arena.safeGetPool(dac));
        sender.sendMessage(msg("select.pool.success"));
    }

    @Command(name = "start", min = 1, max = 1, argsFlags = "s")
    public void start(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        WESelector selectorType = args.get('s', WESelector.class)
                .valueOr(WESelector.Default);

        setSelection(sender, arena, selectorType,
                arena.safeGetStartRegion(dac));
        sender.sendMessage(msg("select.start.success"));
    }

    @Command(name = "surrounding", min = 1, max = 1, argsFlags = "s")
    public void surrounding(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        WESelector selectorType = args.get('s', WESelector.class)
                .valueOr(WESelector.Default);

        setSelection(sender, arena, selectorType,
                arena.safeGetSurroundingRegion(dac));
        sender.sendMessage(msg("select.surrounding.success"));
    }

    private void setSelection(Player sender, Arena arena,
            WESelector selectorType, Region region) {
        Shape shape = region.getShape();
        LocalWorld world = BukkitUtil.getLocalWorld(arena.getWorld());
        WorldEditPlugin worldEdit = getWorldEdit();
        RegionSelector sel = selectorType.create(dac, world, shape);
        LocalSession session = worldEdit.getSession(sender);
        session.setRegionSelector(world, sel);
        session.dispatchCUISelection(worldEdit.wrapPlayer(sender));
    }
}
