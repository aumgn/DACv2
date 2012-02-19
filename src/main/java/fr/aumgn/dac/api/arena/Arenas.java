package fr.aumgn.dac.api.arena;

import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Class responsible for managing available {@link DACArena}. 
 */
public interface Arenas extends Iterable<Arena> {

    /**
     * Load arenas.
     */
    void load();

    /**
     * Save arenas.
     */
    void dump();

    /**
     * Get the arena by the given name. Returns null if does not exist. 
     * 
     * @param name the name of the arena to get
     * @return the arena or null if doesn't exist
     */
    Arena get(String name);
    
    /**
     * Get the arena in which start area the given player is.
     * Returns null if player is not in an arena.
     * 
     * @param player the player in the arena to looks up for
     * @return the arena in which the player is or null if not in any arena
     */
    Arena get(Player player);

    /**
     * Creates a new arena in the given World with the given world.
     *  
     * @param name the name of the new Arena
     * @param world the world of the new Arena
     */
    void createArena(String name, World world);

    /**
     * Removes the given arena.
     *  
     * @param arena the arena to remove
     */
    void removeArena(Arena arena);

}
