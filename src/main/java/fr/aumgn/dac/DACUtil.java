package fr.aumgn.dac;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockVector;

import com.sk89q.worldedit.Vector2D;

public final class DACUtil {
	
	public static final int TICKS_PER_SECONDS = 20;
	public static final int PLAYER_MAX_HEALTH = 20;
	public static final int SIGN_MAX_CHAR = 16;
	public static final int SIGN_LINES = 4;
	
	private static final double BLOCK_LIMIT_LEFT = 0.3;
	private static final double BLOCK_LIMIT_RIGHT = 0.7;
	
	private static final int SIGN_HORIZONTAL_FACES = 16;
	private static final double SIGN_FACES_ANGLE = Math.PI / SIGN_HORIZONTAL_FACES; 
	private static final double MOD_0_TO_1 = Math.cos(7 * SIGN_FACES_ANGLE);
	private static final double MOD_1_TO_2 = Math.cos(3 * SIGN_FACES_ANGLE);
	private static final double MOD_2_TO_1 = Math.cos(SIGN_FACES_ANGLE);
	
	private static final Pattern COLORS_PATTERN = Pattern.compile("<([A-Za-z]+)>"); 
	
	private DACUtil() {} 
	
	private static int getBlockFaceModValue(double i) {
		if (i < -1 || i > 1) {
			throw new IllegalArgumentException("Value must be between -1 and 1");
		}
		else if (i < -MOD_2_TO_1)  { return -1; }
		else if (i < -MOD_1_TO_2)  { return -2; }
		else if (i < -MOD_0_TO_1)  { return -1; }
		else if (i >  MOD_2_TO_1)  { return  1; }
		else if (i >  MOD_1_TO_2)  { return  2; }
		else if (i >  MOD_0_TO_1)  { return  1; }
		else                       { return  0; }
	}
	
	public static BlockFace getHorizontalFaceFor(Vector2D vec) {
		try {
			Vector2D dir = vec.normalize();
			int modX = getBlockFaceModValue(dir.getX());
			int modZ = getBlockFaceModValue(dir.getZ());
			for (BlockFace face : BlockFace.values()) {
				if (face.getModY() == 0 && face.getModX() == modX && face.getModZ() == modZ) {
					return face;
				}
			}
			return BlockFace.SELF;
		} catch (IllegalArgumentException exc) {
			return BlockFace.SELF;
		}
	}
	
	private static int getRelativeModValue(double decimal) {
		if (decimal < BLOCK_LIMIT_LEFT)  { return -1; }
		if (decimal > BLOCK_LIMIT_RIGHT) { return  1; }
		return 0;
	}
	
	private static boolean isRelativeBlockSolid(Block from, int modX, int modZ) {
		Block block = from.getRelative(modX, 0, modZ);
		return !block.isEmpty() && !block.isLiquid();
	}

	public static BlockVector getDeathBlockVector(Location loc) {
		Block block = loc.getBlock().getRelative(0, -1, 0);
		if (!block.isEmpty() && !block.isLiquid()) {
			return new BlockVector(block.getX(), block.getY() + 1, block.getZ());
		}
		
		double x = Math.abs(loc.getX());
		double z = Math.abs(loc.getZ());
		double xDec = x - Math.floor(x);
		double zDec = z - Math.floor(z);
		int modX = getRelativeModValue(xDec);
		int modZ = getRelativeModValue(zDec);

		if (modX != 0 && isRelativeBlockSolid(block, modX, 0)) {
			return new BlockVector(block.getX() + modX, block.getY() + 1, block.getZ());
		}
		if (modZ != 0 && isRelativeBlockSolid(block, 0, modZ)) {
			return new BlockVector(block.getX(), block.getY() + 1, block.getZ() + modZ);
		}
		if (modX != 0 && modZ != 0 && isRelativeBlockSolid(block, modX, modZ)) {
			return new BlockVector(block.getX() + modX, block.getY() + 1, block.getZ() + modZ);
		}
	
		return null;
	}

	public static String parseColorsMarkup(String message) {
		StringBuffer parsed = new StringBuffer();
		Matcher matcher = COLORS_PATTERN.matcher(message);
		while (matcher.find()) {
			try {
				ChatColor color = ChatColor.valueOf(matcher.group(1).toUpperCase()); 
				matcher.appendReplacement(parsed, color.toString());
			} catch (IllegalArgumentException exc) {
				String error = "Invalid color identifier in ";
				error += message + " : " + matcher.group(1);
				DAC.getDACLogger().warning(error);
			}
		}
		matcher.appendTail(parsed);
		return parsed.toString();
	}


	
}