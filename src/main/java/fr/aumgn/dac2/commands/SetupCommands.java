package fr.aumgn.dac2.commands;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.Diving;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.arena.regions.StartRegion;
import fr.aumgn.dac2.arena.regions.WERegionFactory;
import fr.aumgn.dac2.exceptions.WERegionIncomplete;

@NestedCommands(name = {"dac2", "set"})
public class SetupCommands extends DACCommands {

    public SetupCommands(DAC dac) {
        super(dac);
    }

    @Command(name = "pool", min = 1, max = 1)
    public void pool(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        Region region = getRegion(sender);
        Pool pool = WERegionFactory.createPool(dac, region);
        arena.setPool(pool);
        sender.sendMessage(msg("set.pool.success"));
    }

    @Command(name = "start", min = 1, max = 1)
    public void start(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        Region region = getRegion(sender);
        StartRegion start = WERegionFactory.createStartRegion(dac, region);
        arena.setStartRegion(start);
        sender.sendMessage(msg("set.start.success"));
    }

    private Region getRegion(Player player) {
        WorldEditPlugin worldEdit = dac.getWorldEdit();
        LocalWorld world = BukkitUtil.getLocalWorld(player.getWorld());
        RegionSelector selector = worldEdit.getSession(player)
                .getRegionSelector(world);
        try {
            return selector.getRegion();
        } catch (IncompleteRegionException exc) {
            throw new WERegionIncomplete(dac);
        }
    }

    @Command(name = "diving", min = 1, max = 1)
    public void diving(Player sender, CommandArgs args) {
        Arena arena = args.get(0, Arena.class).value();
        arena.setDiving(new Diving(sender.getLocation()));
        sender.sendMessage(msg("set.diving.success"));
    }
}
