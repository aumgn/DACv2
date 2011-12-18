package fr.aumgn.dac.config;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import com.sk89q.worldedit.BlockVector;

public class Pool extends DACArea {

	private static final Material columnMaterial = Material.WOOL; 
	private static final Material defaultMaterial = Material.STATIONARY_WATER;
	private static final Material dacMaterial = Material.GLASS;

	public Pool(DACArena arena) {
		super(arena);
	}
	
	public Pool(DACArena arena, ConfigurationSection section) {
		super(arena, section);
	}

	public void reset() {
		for (BlockVector vec : this) {
			arena.getWorld().getBlockAt(
				vec.getBlockX(), 
				vec.getBlockY(), 
				vec.getBlockZ()
			).setType(defaultMaterial);
		}
	}
	
	public void putColumn(int x, int z, byte color) {
		int y = getMinimumPoint().getBlockY();
		int yMax = getMaximumPoint().getBlockY();
		World world = arena.getWorld();
		for (; y<=yMax; y++) { 
			Block block = world.getBlockAt(x, y, z);
			block.setType(columnMaterial);
			block.setData(color);
		}
	}
	
	public void putDACColumn(int x, int z, byte color) {
		int y = getMinimumPoint().getBlockY();
		int yMax = getMaximumPoint().getBlockY();
		World world = arena.getWorld();
		for (; y<yMax; y++) { 
			Block block = world.getBlockAt(x, y, z);
			block.setType(columnMaterial);
			block.setData(color);
		}
		Block block = world.getBlockAt(x, y, z);
		block.setType(dacMaterial);
	}
	
	private boolean isColumnAt(int x, int z) {
		Block blk = arena.getWorld().getBlockAt(x, getMinimumPoint().getBlockY(), z);
		return !blk.getType().equals(defaultMaterial);
	}
	
	public boolean isADACPattern(int x, int z) {
		boolean dac;
		dac =  isColumnAt(x-1, z);
		dac &= isColumnAt(x, z-1);
		dac &= isColumnAt(x+1, z);
		dac &= isColumnAt(x, z+1);
		return dac;
	}

}
