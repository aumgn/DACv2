package fr.aumgn.dac.command;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.exception.InvalidRegionType;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class SetCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 2) { return false; }
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			context.error(DACMessage.CmdSetUnknown);
			return true;
		}
		String currentWorld = context.getPlayer().getWorld().getName();
		if (!arena.getWorld().getName().equals(currentWorld)) {
			context.error(DACMessage.CmdSetWrongWorld);
			return true;
		}
		try {
			DACMessage message;
			if (args[1].equalsIgnoreCase("diving")) {
				arena.getDivingBoard().update(context.getPlayer().getLocation());
				message = DACMessage.CmdSetSuccessDiving;
			} else if (args[1].equalsIgnoreCase("pool")) {
				arena.getPool().update(getRegion(context));
				message = DACMessage.CmdSetSuccessPool;
			} else if (args[1].equalsIgnoreCase("start")) {
				arena.getStartArea().update(getRegion(context));
				message = DACMessage.CmdSetSuccessStart;
			} else {
				return false;
			}
			context.success(message);
			return true;			
		} catch (IncompleteRegionException e) {
			context.error(DACMessage.CmdSetIncompleteRegion);
			return true;
		} catch (InvalidRegionType exc) {
			context.error(DACMessage.CmdSetError);
			context.error(exc.getMessage());
			return true;
		}
	}

	private Region getRegion(Context context) throws IncompleteRegionException {
		WorldEditPlugin worldEdit = DAC.getWorldEdit();
		BukkitWorld world = new BukkitWorld(context.getPlayer().getWorld());
		RegionSelector selector;
		selector = worldEdit.getSession(context.getPlayer()).getRegionSelector(world);
		return selector.getRegion();
	}

}
