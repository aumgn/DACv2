package fr.aumgn.dac.config;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;

public class Pool extends DACArea {

	private static final Material columnMaterial = Material.WOOL; 
	private static final Material defaultMaterial = Material.STATIONARY_WATER;
	private static final Material dacMaterial = Material.GLASS;

	public Pool(DACArena arena) {
		super(arena);
	}
	
	public void reset() {
		EditSession editSession = new EditSession(arena.getWEWorld(), -1);
		try {
			editSession.setBlocks(region, new BaseBlock(defaultMaterial.getId())); 
		} catch (MaxChangedBlocksException e) {}
	}
	
	public void putColumn(int x, int z, byte color) {
		int y = region.getMinimumPoint().getBlockY();
		int yMax = region.getMaximumPoint().getBlockY();
		World world = arena.getWorld();
		for (; y<=yMax; y++) { 
			Block block = world.getBlockAt(x, y, z);
			block.setType(columnMaterial);
			block.setData(color);
		}
	}
	
	public void putDACColumn(int x, int z, byte color) {
		int y = region.getMinimumPoint().getBlockY();
		int yMax = region.getMaximumPoint().getBlockY();
		World world = arena.getWorld();
		for (; y<yMax; y++) { 
			Block block = world.getBlockAt(x, y, z);
			block.setType(columnMaterial);
			block.setData(color);
		}
		Block block = world.getBlockAt(x, y, z);
		block.setType(dacMaterial);
	}
	
	public void putRIPSign(Location location, String name) {
		int yMax = region.getMaximumPoint().getBlockY();
		Block block = arena.getWorld().getBlockAt(location.getBlockX(), yMax+1, location.getBlockZ());
		block.setType(Material.SIGN_POST);
		Sign sign = (Sign)block.getState();
		sign.setLine(0, "RIP");
	}
	
	public void rip(Location location, String name) {
		int yMax = region.getMaximumPoint().getBlockY();
		Block block = arena.getWorld().getBlockAt(location.getBlockX(), yMax+1, location.getBlockZ());
		if (block.getType() != Material.SIGN_POST) {
			putRIPSign(location, name);
		}
		for (int i=1; i<4; i++) {
			Sign sign = (Sign)block.getState();
			if (sign.getLine(i).isEmpty()) {
				sign.setLine(i, name);
				break;
			}
		}
	}
	
	private boolean isColumnAt(int x, int z) {
		Block blk = arena.getWorld().getBlockAt(x, region.getMinimumPoint().getBlockY(), z);
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
