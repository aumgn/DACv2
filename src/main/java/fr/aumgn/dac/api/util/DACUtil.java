package fr.aumgn.dac.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;

import fr.aumgn.dac.api.DAC;

/**
 * Utility static class
 */
public final class DACUtil {

    public static final int TICKS_PER_SECONDS = 20;
    public static final int PLAYER_MAX_HEALTH = 20;
    /** Maximum number of character per sign line. */
    public static final int SIGN_MAX_CHAR = 16;
    /** Number of lines per sign. */
    public static final int SIGN_LINES = 4;

    /** Decimal Minecraft block bounding box left limit. */
    private static final double BLOCK_LIMIT_LEFT = 0.3;
    /** Decimal Minecraft block bounding box right limit.*/
    private static final double BLOCK_LIMIT_RIGHT = 0.7;

    private static final Pattern COLORS_PATTERN = Pattern.compile("<([A-Za-z]+)>");

    private DACUtil() {}

    /**
     * Gets the block relative modifier for the given value.
     * That is if the value is :
     *   decimal(value) < 0.3 => -1
     *   decimal(value) > 0.7 => 1
     *   anything else => 0 
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

    private static boolean isRelativeBlockSolid(Block from, int modX, int modZ) {
        Block block = from.getRelative(modX, 0, modZ);
        return !block.isEmpty() && !block.isLiquid();
    }

    /**
     * Gets the Vector representing the block above the one which is responsible for player's damage.
     */
    public static Vector getDamageBlockVector(Location loc) {
        Block block = loc.getBlock().getRelative(0, -1, 0);
        if (!block.isEmpty() && !block.isLiquid()) {
            return new Vector(block.getX(), block.getY() + 1, block.getZ());
        }

        int modX = getRelativeModValue(loc.getX());
        int modZ = getRelativeModValue(loc.getZ());

        if (modX != 0 && isRelativeBlockSolid(block, modX, 0)) {
            return new Vector(block.getX() + modX, block.getY() + 1, block.getZ());
        }
        if (modZ != 0 && isRelativeBlockSolid(block, 0, modZ)) {
            return new Vector(block.getX(), block.getY() + 1, block.getZ() + modZ);
        }
        if (modX != 0 && modZ != 0 && isRelativeBlockSolid(block, modX, modZ)) {
            return new Vector(block.getX() + modX, block.getY() + 1, block.getZ() + modZ);
        }

        return null;
    }

    /**
     * Parses colors markups.
     * For example : ({@literal '<red>'} will be converted to ChatColor.RED.toString()
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

    public static int parseInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException exc) {
            return 0;
        }
    }

    public static void fakeDamage(Player player) {
        int health = player.getHealth(); 
        if (health == PLAYER_MAX_HEALTH) {
            player.damage(1);
            player.setHealth(health);
        } else {
            player.setHealth(health + 1);
            player.damage(1);
        }
    }

}