package fr.aumgn.dac.areas;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.CylinderRegion;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.areas.column.DACColumn;
import fr.aumgn.dac.areas.filler.DACAreaFiller;
import fr.aumgn.dac.areas.region.DACCuboid;
import fr.aumgn.dac.areas.region.DACCylinder;
import fr.aumgn.dac.areas.region.DACPolygonal;
import fr.aumgn.dac.areas.region.DACRegion;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.exception.InvalidRegionType;

public class DACArea implements Iterable<Block> {

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
		} else if (region instanceof Polygonal2DRegion) {
			Polygonal2DRegion poly = (Polygonal2DRegion)region;
			this.region = new DACPolygonal(poly);
		} else if (region instanceof CylinderRegion) {
			CylinderRegion cyl = (CylinderRegion)region;
			this.region = new DACCylinder(cyl);
		} else {
			throw new InvalidRegionType("CuboidRegion", "Polygonal2DRegion", region.getClass());			
		}
		arena.updated();
	}

	public DACArena getArena() {
		return arena;
	}

	public DACRegion getRegion() {
		return region;
	}

	public Region getWERegion() {
		return region.getRegion(arena.getWEWorld());
	}

	public void setRegion(DACRegion region) {
		this.region = region;
	}

	public Selection getSelection() {
		return region.getSelection(arena.getWorld());
	}

	public int getMinimumY() {
		return getWERegion().getMinimumPoint().getBlockY();
	}

	public int getMaximumY() {
		return getWERegion().getMaximumPoint().getBlockY();
	}

	public boolean contains(Player player) {
		return contains(player.getLocation());
	}

	public boolean contains(Location location) {
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		return region.getRegion(arena.getWEWorld()).contains(new BlockVector(x, y, z));
	}
	
	public void fillWith(DACAreaFiller filler) {
		filler.fill(this);
	}
	
	@Override
	public Iterator<Block> iterator() {
		return new DACAreaIterator(this, getWERegion().iterator());
	}
	
	public Iterable<DACColumn> columns() {
		return new Iterable<DACColumn>() {
			@Override
			public Iterator<DACColumn> iterator() {
				return new DACAreaVerticalIterator(DACArea.this);
			}
		};
	}

}
