package fr.aumgn.dac.arenas.region;

import org.bukkit.World;

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;

public abstract class DACBasicRegion implements DACRegion {
	
	private Region region; 

	public abstract Region createWERegion(LocalWorld world);
	
	public abstract Selection getSelection(World world);
	
	public Region getRegion(LocalWorld world) {
		if (region == null || region.getWorld() != world) {
			region = createWERegion(world);
		}
		return region;
	}
	
}