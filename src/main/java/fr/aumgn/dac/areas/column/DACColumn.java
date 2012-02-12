package fr.aumgn.dac.areas.column;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.bukkit.World;
import org.bukkit.block.Block;

import fr.aumgn.dac.arenas.Pool;

public class DACColumn implements Iterable<Block> {
	
	private class DACColumnIterator implements Iterator<Block> {
		
		private int y;
		
		public DACColumnIterator() {
			this.y = yMin;
		}
		
		@Override
		public boolean hasNext() {
			return y <= yMax;
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
	private int yMin;
	private int yMax; 
	private int x;
	private int z;

	public DACColumn(Pool pool, int x, int z) {
		this.world = pool.getArena().getWorld();
		this.yMin = pool.getMinimumY();
		this.yMax = pool.getMaximumY();
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

	public int getYMin() {
		return yMin;
	}

	public int getYMax() {
		return yMax;
	}
	
	@Override
	public Iterator<Block> iterator() {
		return new DACColumnIterator(); 
	}

}
