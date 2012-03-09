package fr.aumgn.dac.api.arena;

import org.bukkit.Location;

/**
 * Represents a diving board of an arena.
 * Basically a wrapper for Bukkit Location.
 */
public interface DivingBoard {

    Location getLocation();

    void update(Location location);

}
