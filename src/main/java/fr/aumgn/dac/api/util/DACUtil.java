package fr.aumgn.dac.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.BlockVector;

import fr.aumgn.dac.api.DAC;

/**
 * Utilities static class
 */
public final class DACUtil {

    public static final int TICKS_PER_SECONDS = 20;
    public static final int PLAYER_MAX_HEALTH = 20;
    public static final int SIGN_MAX_CHAR = 16;
    public static final int SIGN_LINES = 4;

    private static final double BLOCK_LIMIT_LEFT = 0.3;
    private static final double BLOCK_LIMIT_RIGHT = 0.7;

    private static final Pattern COLORS_PATTERN = Pattern.compile("<([A-Za-z]+)>");

    private DACUtil() {}

    /**
     * Gets the block relative modifier for the given value.
     * That is if the value is :
     *   decimal(value) < 0.3 => -1
     *   decimal(value) > 0.7 => 1  
     * 
     * @param value the value to get the modifier for
     * @return the relative modifier
     */
    private static int getRelativeModValue(double value) {
        double abs = Math.abs(value);
        double decimal = abs - Math.floor(abs);
        if (decimal < BLOCK_LIMIT_LEFT) {
            return -1;
        }
        if (decimal > BLOCK_LIMIT_RIGHT) {
            return 1;
        }
        return 0;
    }

    /**
     * Checks if relative blocks is solid
     *  (ie. isn't empty or liquid)
     * 
     * @return whether the relative block is solid
     */
    private static boolean isRelativeBlockSolid(Block from, int modX, int modZ) {
        Block block = from.getRelative(modX, 0, modZ);
        return !block.isEmpty() && !block.isLiquid();
    }

    /**
     * Gets the BlockVector of the block which is responsible for player's damage.
     * 
     * @param loc the location where the player die
     * @return the block vector responsible for player's damage
     */
    public static BlockVector getDamageBlockVector(Location loc) {
        Block block = loc.getBlock().getRelative(0, -1, 0);
        if (!block.isEmpty() && !block.isLiquid()) {
            return new BlockVector(block.getX(), block.getY() + 1, block.getZ());
        }

        int modX = getRelativeModValue(loc.getX());
        int modZ = getRelativeModValue(loc.getZ());

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

    /**
     * Parses colors markups.
     * For example : ({@literal '<red>'} will be converted to ChatColor.RED.toString()
     *    
     * @param message the messages to parse
     * @return the parsed message
     */
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
                DAC.getLogger().warning(error);
            }
        }
        matcher.appendTail(parsed);
        return parsed.toString();
    }

}