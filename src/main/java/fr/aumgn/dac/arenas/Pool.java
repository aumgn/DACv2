package fr.aumgn.dac.arenas;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.DAC;
import static fr.aumgn.dac.DACUtil.getHorizontalFaceFor;
import fr.aumgn.dac.config.DACColor;

@SerializableAs("dac-pool")
public class Pool extends DACArea {

	private static final Material DEFAULT_MATERIAL = Material.STATIONARY_WATER;
	private static final Material DAC_MATERIAL = Material.GLASS;
	private static final Material SIGN_MATERIAL = Material.SIGN_POST;
	private static final Material AIR = Material.AIR; 
	private static final BaseBlock WATER = new BaseBlock(DEFAULT_MATERIAL.getId());
	
	private static final int ABOVE_REGION_HEIGHT = 5;
	private static final int ABOVE_REGION_MARGIN = 5;

	public Pool(DACArena arena) {
		super(arena);
	}

	public void reset() {
		EditSession editSession = new EditSession(getArena().getWEWorld(), -1);
		try {
			World world = getArena().getWorld();
			CuboidRegion above = getAboveRegion();
			for (BlockVector vec : above) {
				Block block = world.getBlockAt(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ()); 
				if (block.getType() == SIGN_MATERIAL) { block.setType(AIR); }
			}
			editSession.setBlocks(getWERegion(), WATER); 
		} catch (MaxChangedBlocksException e) {
			String warning = "A weird exception occured while trying to reset ";
			warning += getArena().getName() + ". Maybe the pool is too Big ?";
			DAC.getDACLogger().warning(warning);
		}
	}
	
	public CuboidRegion getAboveRegion() {
		int minY, maxY;
		Vector poolMinPt, poolMaxPt, minPt, maxPt;
		Region region = getWERegion();
		poolMinPt = region.getMinimumPoint();
		poolMaxPt = region.getMaximumPoint();
		
		minY = poolMaxPt.getBlockY() + 1;
		maxY = minY + ABOVE_REGION_HEIGHT;
		
		minPt = poolMinPt.subtract(ABOVE_REGION_MARGIN, 0, ABOVE_REGION_MARGIN).setY(minY);
		maxPt = poolMaxPt.add(ABOVE_REGION_MARGIN, 0, ABOVE_REGION_MARGIN).setY(maxY);
		
		return new CuboidRegion(region.getWorld(), minPt, maxPt);
	}
	
	public boolean isAbove(Player player) {
		Vector vec = new BlockVector(
			player.getLocation().getBlockX(),
			player.getLocation().getBlockY(),
			player.getLocation().getBlockZ()
		);
		return getAboveRegion().contains(vec);
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
		block.setType(DAC_MATERIAL);
	}

	public void putRIPSign(org.bukkit.util.Vector vec) {
		Location diving = getArena().getDivingBoard().getLocation();
		
		Vector2D dir = new Vector2D(diving.getX()-vec.getX(), diving.getZ()-vec.getZ());
		BlockFace face = getHorizontalFaceFor(dir);
		
		Block block = getArena().getWorld().getBlockAt(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
		block.setType(Material.SIGN_POST);
		
		org.bukkit.material.Sign signBlock = new org.bukkit.material.Sign(Material.SIGN_POST, block.getData());
		signBlock.setFacingDirection(face);
		block.setData(signBlock.getData());
		
		Sign sign = (Sign)block.getState();
		sign.setLine(0, DAC.getDACConfig().getDeathSignFirstLine());
		sign.update();
	}

	public void rip(org.bukkit.util.Vector vec, String name) {
		if (vec == null) { return; }
		Block block = getArena().getWorld().getBlockAt(
			vec.getBlockX(),
			vec.getBlockY(),
			vec.getBlockZ()
		);
		if (block.getType() != Material.SIGN_POST) {
			putRIPSign(vec);
		}
		for (int i=1; i<4; i++) {
			Sign sign = (Sign)block.getState();
			if (sign.getLine(i).isEmpty()) {
				sign.setLine(i, name);
				break;
			}
			sign.update();
		}
	}

	private boolean isColumnAt(int x, int z) {
		Block blk = getArena().getWorld().getBlockAt(x, getWERegion().getMinimumPoint().getBlockY(), z);
		return !blk.getType().equals(DEFAULT_MATERIAL);
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
