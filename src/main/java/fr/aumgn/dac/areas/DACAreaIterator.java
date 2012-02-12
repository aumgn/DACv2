package fr.aumgn.dac.areas;

import java.util.Iterator;

import org.bukkit.World;
import org.bukkit.block.Block;

import com.sk89q.worldedit.BlockVector;

public class DACAreaIterator implements Iterator<Block> {

	private World world;
	private Iterator<BlockVector> weIterator;
	
	public DACAreaIterator(DACArea area, Iterator<BlockVector> weIterator) {
		this.world = area.getArena().getWorld();
		this.weIterator = weIterator;
	}
	
	@Override
	public boolean hasNext() {
		return weIterator.hasNext();
	}

	@Override
	public Block next() {
		BlockVector next = weIterator.next();
		return world.getBlockAt(next.getBlockX(), next.getBlockY(), next.getBlockZ());
	}

	@Override
	public void remove() {
		weIterator.remove();
	}

}
