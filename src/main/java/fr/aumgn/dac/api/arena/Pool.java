package fr.aumgn.dac.api.arena;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.area.VerticalArea;

/**
 * Represents a pool in an arena.
 */
public interface Pool extends VerticalArea {

    /**
     * Checks if the given player is above a pool.
     * This is where he can jump without getting
     */
    boolean isSafe(Player player);

    /**
     * Resets the pool. (ie. fill it with water) 
     */
    void reset();

    /**
     * Checks if the given horizontal position is a 'dé a coudre'
     * ie. this position is surrounded by four column which are
     * not water column.
     */
    boolean isADACPattern(int x, int z);

    void updateSafeRegion();

}
