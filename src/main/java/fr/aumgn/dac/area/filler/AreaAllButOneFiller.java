package fr.aumgn.dac.area.filler;

import java.util.Random;

import org.bukkit.Material;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.area.Area;
import fr.aumgn.dac.area.column.AreaColumn;

public class AreaAllButOneFiller implements AreaFiller {

	@Override
	public void fill(Area area) {
		Random rand = new Random();
		Region region = area.getWERegion();
		Vector minPoint = region.getMinimumPoint();
		Vector maxPoint = region.getMaximumPoint();
		int x, z, y = minPoint.getBlockY();
		int minX = minPoint.getBlockX();
		int minZ = minPoint.getBlockZ();
		int xRange = maxPoint.getBlockX() - minX;
		int zRange = maxPoint.getBlockZ() - minZ;
		do {
			x = minX + rand.nextInt(xRange);
			z = minZ + rand.nextInt(zRange);
		} while (!region.contains(new BlockVector(x, y, z)));
		
		for (AreaColumn column : area.columns()) {
			if (column.getX() != x || column.getZ() != z) {
				column.set(Material.WOOL);
			} else {
				column.set(Material.STATIONARY_WATER);				
			}
		}
	}

}
