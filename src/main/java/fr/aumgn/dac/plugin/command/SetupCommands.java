package fr.aumgn.dac.plugin.command;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.CommandArgs;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.exception.DACException;
import fr.aumgn.dac.api.exception.InvalidRegionType;

@NestedCommands(name = {"dac", "set"})
public class SetupCommands extends DACCommands {

    @Command(name = "pool", min = 1, max = 1)
    public void pool(Player player, CommandArgs args) {
        Arena arena = getArena(player, args);

        try {
            arena.getPool().update(getRegion(player));
            success(player, DACMessage.CmdSetSuccessPool);
        } catch (IncompleteRegionException e) {
            throw new DACException(DACMessage.CmdSetIncompleteRegion);
        } catch (InvalidRegionType exc) {
            throw new DACException(DACMessage.CmdSetError);
        }
    }

    @Command(name = "start", min = 1, max = 1)
    public void start(Player player, CommandArgs args) {
        Arena arena = getArena(player, args);

        try {
            arena.getStartArea().update(getRegion(player));
            success(player, DACMessage.CmdSetSuccessStart);
        } catch (IncompleteRegionException e) {
            throw new DACException(DACMessage.CmdSetIncompleteRegion);
        } catch (InvalidRegionType exc) {
            throw new DACException(DACMessage.CmdSetError);
        }
    }

    @Command(name = "diving", min = 1, max = 1)
    public void diving(Player player, CommandArgs args) {
        Arena arena = getArena(player, args);

        arena.getDivingBoard().update(player.getLocation());
        success(player, DACMessage.CmdSetSuccessDiving);
    }

    private Arena getArena(Player player, CommandArgs args) {
        Arena arena = DAC.getArenas().get(args.get(0));
        if (arena == null) {
            throw new DACException(DACMessage.CmdSetUnknown);
        }

        String currentWorld = player.getWorld().getName();
        if (!arena.getWorld().getName().equals(currentWorld)) {
            throw new DACException(DACMessage.CmdSetWrongWorld);
        }

        return arena;
    }

    private Region getRegion(Player player) throws IncompleteRegionException {
        WorldEditPlugin worldEdit = DAC.getWorldEdit();
        BukkitWorld world = new BukkitWorld(player.getWorld());
        RegionSelector selector;
        selector = worldEdit.getSession(player).getRegionSelector(world);
        return selector.getRegion();
    }

}
