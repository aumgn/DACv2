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
	public boolean checkUsage(String[] args) {
		return args.length == 2 && ( 
			args[1].equalsIgnoreCase("diving") ||
			args[1].equalsIgnoreCase("pool") ||
			args[1].equalsIgnoreCase("start")
		);
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			error(DACMessage.CmdSetUnknown);
		}
		
		String currentWorld = context.getPlayer().getWorld().getName();
		if (!arena.getWorld().getName().equals(currentWorld)) {
			error(DACMessage.CmdSetWrongWorld);
		}
		
		try {
			if (args[1].equalsIgnoreCase("diving")) {
				arena.getDivingBoard().update(context.getPlayer().getLocation());
				context.success(DACMessage.CmdSetSuccessDiving);
			} else if (args[1].equalsIgnoreCase("pool")) {
				arena.getPool().update(getRegion(context));
				context.success(DACMessage.CmdSetSuccessPool);
			} else if (args[1].equalsIgnoreCase("start")) {
				arena.getStartArea().update(getRegion(context));
				context.success(DACMessage.CmdSetSuccessStart);
			}
		} catch (IncompleteRegionException e) {
			error(DACMessage.CmdSetIncompleteRegion);
		} catch (InvalidRegionType exc) {
			context.error(DACMessage.CmdSetError);
			error(exc.getMessage());
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
