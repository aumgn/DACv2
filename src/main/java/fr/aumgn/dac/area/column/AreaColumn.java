package fr.aumgn.dac.area.column;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import fr.aumgn.dac.area.Area;

public class AreaColumn implements Iterable<Block> {
	
	private class DACColumnIterator implements Iterator<Block> {
		
		private int y;
		
		public DACColumnIterator() {
			this.y = bottom;
		}
		
		@Override
		public boolean hasNext() {
			return y <= top;
		}

		@Override
		public Block next() {
			if (!hasNext()) {
				 throw new NoSuchElementException();
			}
			
			Block block = world.getBlockAt(x, y, z);
			y++;
			return block;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
	
	private World world;
	private int bottom;
	private int top; 
	private int x;
	private int z;

	public AreaColumn(Area area, int x, int z) {
		this.world = area.getArena().getWorld();
		this.bottom = area.getMinimumY();
		this.top = area.getMaximumY();
		this.x = x;
		this.z = z;
	}

	public World getWorld() {
		return world;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public int getBottom() {
		return bottom;
	}

	public int getTop() {
		return top;
	}
	
	public void set(Material material, byte data) {
		for (Block block : this) {
			block.setType(material);
			block.setData(data);
		}
	}
	
	public void set(Material material) {
		for (Block block : this) {
			block.setType(material);
		}
	}
	
	@Override
	public Iterator<Block> iterator() {
		return new DACColumnIterator(); 
	}

}
