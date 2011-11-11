package fr.aumgn.dac.command;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.DAC;
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
			context.error("Arene inconnu.");
			return true;
		}
		if (arena.getWorld().getName() != context.getPlayer().getWorld().getName()) {
			context.error("L'arene est defini dans un autre monde.");
			return true;
		}
		if (args[1].equalsIgnoreCase("diving")) {
			arena.getDivingBoard().update(context.getPlayer().getLocation());
			context.success("La position sur le plongeoir a ete mise a jour.");
			return true;
		}
		if (args[1].equalsIgnoreCase("pool")) {
			CuboidRegion region = getRegion(context);
			if (region != null) {
				arena.getPool().update(getRegion(context));
				context.success("La zone du bassin a ete mise a jour.");
			}
			return true;
		}
		if (args[1].equalsIgnoreCase("start")) {
			CuboidRegion region = getRegion(context);
			if (region != null) {
				arena.getStartArea().update(region);
				context.success("La zone de depart a ete mise a jour.");
			}
			return true;
		}
		return false;
	}

	private CuboidRegion getRegion(Context context) {
		try {
			WorldEditPlugin worldEdit = plugin.getWorldEdit();
			Region region;
			region = worldEdit.getSession(context.getPlayer()).getRegionSelector().getRegion();
			if (!(region instanceof CuboidRegion))
				context.error("La selection WorldEdit actuelle n'est pas un cuboid");
			else
				return (CuboidRegion)region;
		} catch (IncompleteRegionException e) {
			context.error("La selection WorldEdit est incomplete");
		}
		return null;
	}

}
