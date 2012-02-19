package fr.aumgn.dac.api.arena;

import org.bukkit.Location;

/**
 * Represents a diving board of an arena.
 * Basically a wrapper for Bukkit Location.
 */
public interface DivingBoard {

    /**
     * Gets the location of this diving board.
     * 
     * @return the location of this diving board.
     */
    Location getLocation();

    /**
     * Updates the diving board whit the given location.
     * @param location the location to update this diving board with.
     */
    void update(Location location);

}
