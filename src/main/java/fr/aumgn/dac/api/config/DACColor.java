package fr.aumgn.dac.api.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * Represents a color used by a player.
 */
public interface DACColor {

    /**
     * Gets the name of the color.
     * @return the name of the color
     */
    String getName();

    /**
     * Gets the ChatColor associated with this color.
     * @return the ChatColor of this color
     */
    ChatColor getChatColor();

    /**
     * Gets the Material associated with this color. 
     * @return the Material of this color
     */
    Material getMaterial();

    /**
     * Gets the block data associated with this color. 
     * @return the block data of this color
     */
    byte getData();

}
