package fr.aumgn.dac;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockVector;

import com.sk89q.worldedit.Vector2D;

public class DACUtil {
	
	private static final double Mod0to1;
	private static final double Mod1to2;
	private static final double Mod2to1;
	
	static {
		double i = Math.PI / 16;
		Mod0to1 = Math.cos(7 * i);
		Mod1to2 = Math.cos(3 * i);
		Mod2to1 = Math.cos(i);
	}
	
	public static int getModValue(double i) {
		if (i < -1 || i > 1) {
			throw new IllegalArgumentException("Value must be between -1 and 1");
		}
		else if (i < -Mod2to1)  { return -1; }
		else if (i < -Mod1to2)  { return -2; }
		else if (i < -Mod0to1)  { return -1; }
		else if (i >  Mod2to1)  { return  1; }
		else if (i >  Mod1to2)  { return  2; }
		else if (i >  Mod0to1)  { return  1; }
		else                    { return  0; }
	}
	
	public static BlockFace getHorizontalFaceFor(Vector2D vec) {
		try {
			Vector2D dir = vec.normalize();
			int modX = getModValue(dir.getX());
			int modZ = getModValue(dir.getZ());
			System.out.println(modX + " - " + modZ);
			for (BlockFace face : BlockFace.values()) {
				if (face.getModY() == 0 && face.getModX() == modX && face.getModZ() == modZ) {
					return face;
				}
			}
			return BlockFace.SELF;
		} catch (IllegalArgumentException exc) {
			return BlockFace.SELF;
		} catch (ArrayIndexOutOfBoundsException exc) {
			return BlockFace.SELF;
		}
	}

	
	public static BlockVector getDeathBlockVector(Location loc) {
		Block block = loc.getBlock().getRelative(0, -1, 0);
		if (!block.isEmpty() && !block.isLiquid()) {
			return new BlockVector(block.getX(), block.getY() + 1, block.getZ());
		}
		
		int modX, modZ;
		double x = loc.getX();
		double z = loc.getZ();
		double xDec = x - Math.ceil(x);
		double zDec = z - Math.ceil(z);
		Block block2;
		if (xDec < 0.3) { modX = -1; }
		else if (xDec > 0.7) { modX = 1; }
		else { modX = 0; }
		if (zDec < 0.3) { modZ = -1; }
		else if (zDec > 0.7) { modZ = 1; }
		else { modZ = 0; }
		if (modX != 0) {
			block2 = block.getRelative(modX, 0, 0);
			if (!block2.isEmpty() && !block2.isLiquid()) {
				return new BlockVector(block2.getX(), block2.getY() + 1, block2.getZ());
			}
		}
		if (modZ != 0) {
			block2 = block.getRelative(0, 0, modZ);
			if (!block2.isEmpty() && !block2.isLiquid()) {
				return new BlockVector(block2.getX(), block2.getY() + 1, block2.getZ());
			}
		}
		if (modX != 0 && modZ != 0) {
			block2 = block.getRelative(modX, 0, modZ);
			if (!block2.isEmpty() && !block2.isLiquid()) {
				return new BlockVector(block2.getX(), block2.getY() + 1, block2.getZ());
			}
		}
		return null;
	}

}