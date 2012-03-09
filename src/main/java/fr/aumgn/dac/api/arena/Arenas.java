package fr.aumgn.dac.api.arena;

import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Class responsible for managing available {@link Arena}. 
 */
public interface Arenas extends Iterable<Arena> {

    void load();

    void dump();

    Arena get(String name);

    /**
     * Gets the arena in which start area the given player is.
     * Returns null if player is not in an arena.
     */
    Arena get(Player player);

    void createArena(String name, World world);

    void removeArena(Arena arena);

}
