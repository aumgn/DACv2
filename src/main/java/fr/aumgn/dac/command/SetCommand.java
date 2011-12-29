package fr.aumgn.dac.command;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACArea.InvalidRegionType;
import fr.aumgn.dac.config.DACArena;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class SetCommand extends PlayerCommandExecutor {
	
	private DAC plugin;
	
	public SetCommand(DAC plugin) {
		this.plugin = plugin; 
	}

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 2) { return false; }
		DACArena arena = plugin.getDACConfig().get(args[0]);
		if (arena == null) {
			context.error("Arène inconnu.");
			return true;
		}
		if (arena.getWorld().getName() != context.getPlayer().getWorld().getName()) {
			context.error("L'arène est défini dans un autre monde.");
			return true;
		}
		if (args[1].equalsIgnoreCase("diving")) {
			arena.getDivingBoard().update(context.getPlayer().getLocation());
			context.success("La position sur le plongeoir a été mise à jour.");
			return true;
		}
		try {
			if (args[1].equalsIgnoreCase("pool")) {
				Region region = getRegion(context);
				if (region != null) {
					arena.getPool().update(getRegion(context));
					context.success("La zone du bassin a été mise a jour.");
				}
				return true;
			}
			if (args[1].equalsIgnoreCase("start")) {
				Region region = getRegion(context);
				if (region != null) {
					arena.getStartArea().update(region);
					context.success("La zone de départ a été mise a jour.");
				}
				return true;
			}
		} catch (InvalidRegionType exc) {
			context.error("Une erreur est survenu durant la mise a jour de la zone.");
			context.error(exc.getMessage());
			return true;
		}
		return false;
	}

	private Region getRegion(Context context) {
		try {
			WorldEditPlugin worldEdit = plugin.getWorldEdit();
			BukkitWorld world = new BukkitWorld(context.getPlayer().getWorld());
			Region region;
			region = worldEdit.getSession(context.getPlayer()).getRegionSelector(world).getRegion();
			return region;
		} catch (IncompleteRegionException e) {
			context.error("La sélection WorldEdit est incomplète");
		}
		return null;
	}

}
