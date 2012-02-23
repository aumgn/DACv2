package fr.aumgn.dac.api.arena;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.area.VerticalArea;

/**
 * Represents a pool in an arena.
 */
public interface Pool extends VerticalArea {

    /**
     * Checks if the given player is above a pool.
     * 
     * @param player the player to checks with
     * @return whether or not the given player is above the pool
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
     * 
     * @param x the x position
     * @param z the z position
     * @return whether or not this position is a 'dé a coudre'
     */
    boolean isADACPattern(int x, int z);

    /**
     * Updates this pool's safe region.
     */
	void updateSafeRegion();

}
