package fr.aumgn.dac.arenas;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.DACException.InvalidRegionType;
import fr.aumgn.dac.arenas.region.DACCuboid;
import fr.aumgn.dac.arenas.region.DACRegion;

public class DACArea {

	private DACArena arena;
	private DACRegion region;

	public DACArea(DACArena arena) {
		this.arena = arena;
		this.region = new DACCuboid();
	}

	public void update(Region region) {
		if (region instanceof CuboidRegion) {
			CuboidRegion cuboid = (CuboidRegion)region;
			this.region = new DACCuboid(cuboid);
		/*} else if (region instanceof Polygonal2DRegion) {
			Polygonal2DRegion poly = (Polygonal2DRegion)region;
			// Workaround
			polygonSerialization = new Polygonal2DSerialization(poly);
			this.region = new Polygonal2DRegion(
					arena.getWEWorld(),
					poly.getPoints(),
					poly.getMinimumPoint().getBlockY(),
					poly.getMaximumPoint().getBlockY()
					);*/
		} else {
			throw new InvalidRegionType("CuboidRegion", "Polygonal2DRegion", region.getClass());			
		}
		arena.updated();
	}

	public DACArena getArena() {
		return arena;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(DACRegion region) {
		this.region = region;
	}

	public Selection getSelection() {
		return region.getSelection(arena.getWorld());
		/*if (region instanceof CuboidRegion) {
			CuboidRegion cuboid = (CuboidRegion)region;
			selection = new CuboidSelection(arena.getWorld(), cuboid.getMinimumPoint(), cuboid.getMaximumPoint());
		} else if (region instanceof Polygonal2DRegion) {
			Polygonal2DRegion poly = (Polygonal2DRegion)region;
			try {
				selection = new Polygonal2DSelection(
						arena.getWorld(),
						poly.getPoints(),
						poly.getMinimumPoint().getBlockY(),
						poly.getMaximumPoint().getBlockY()
						);	
			} catch (IndexOutOfBoundsException exc) {
				throw new InvalidRegionType(
						"La reselection des zones polygonales n'est pas supportée "
								+ "avec votre version de WorldEdit a cause d'un bug (resolu dans les builds plus récent)."
						);
			}
		} else {
			throw new InvalidRegionType("CuboidRegion", "Polygonal2DRegion", region.getClass());			
		}
		return selection;*/
	}

	public boolean contains(Player player) {
		return contains(player.getLocation());
	}

	public boolean contains(Location location) {
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		return region.contains(new BlockVector(x, y, z));
	}

}
