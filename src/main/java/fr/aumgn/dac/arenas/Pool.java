package fr.aumgn.dac.arenas;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACColor;

@SerializableAs("dac-pool")
public class Pool extends DACArea {

	private static final Material DefaultMaterial = Material.STATIONARY_WATER;
	private static final Material DacMaterial = Material.GLASS;

	public Pool(DACArena arena) {
		super(arena);
	}

	public void reset() {
		EditSession editSession = new EditSession(getArena().getWEWorld(), -1);
		try {
			editSession.setBlocks(getWERegion(), new BaseBlock(DefaultMaterial.getId())); 
		} catch (MaxChangedBlocksException e) {
			String warning = "A weird exception has occured while trying to reset ;";
			warning += getArena().getName() + " pool. Maybe the pool is too Big ?";
			DAC.getDACLogger().warning(warning);
		}
	}

	public void putColumn(int x, int z, DACColor color) {
		Region region = getWERegion();
		int y = region.getMinimumPoint().getBlockY();
		int yMax = region.getMaximumPoint().getBlockY();
		World world = getArena().getWorld();
		for (; y<=yMax; y++) { 
			Block block = world.getBlockAt(x, y, z);
			block.setType(color.getMaterial());
			block.setData(color.getData());
		}
	}

	public void putDACColumn(int x, int z, DACColor color) {
		Region region = getWERegion(); 
		int y = region.getMinimumPoint().getBlockY();
		int yMax = region.getMaximumPoint().getBlockY();
		World world = getArena().getWorld();
		for (; y<yMax; y++) { 
			Block block = world.getBlockAt(x, y, z);
			block.setType(color.getMaterial());
			block.setData(color.getData());
		}
		Block block = world.getBlockAt(x, y, z);
		block.setType(DacMaterial);
	}

	public void putRIPSign(Location location, String name) {
		int yMax = getWERegion().getMaximumPoint().getBlockY();
		Block block = getArena().getWorld().getBlockAt(location.getBlockX(), yMax+1, location.getBlockZ());
		block.setType(Material.SIGN_POST);
		Sign sign = (Sign)block.getState();
		sign.setLine(0, "RIP");
	}

	public void rip(Location location, String name) {
		int yMax = getWERegion().getMaximumPoint().getBlockY();
		Block block = getArena().getWorld().getBlockAt(location.getBlockX(), yMax+1, location.getBlockZ());
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
		Block blk = getArena().getWorld().getBlockAt(x, getWERegion().getMinimumPoint().getBlockY(), z);
		return !blk.getType().equals(DefaultMaterial);
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
