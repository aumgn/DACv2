package fr.aumgn.dac.api.arena;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.World;

import com.sk89q.worldedit.LocalWorld;

import fr.aumgn.dac.api.game.GameOptions;

/**
 * Represents a DAC arena.
 */
public interface Arena {

    String getName();

    World getWorld();

    LocalWorld getWEWorld();

    DivingBoard getDivingBoard();

    Pool getPool();

    StartArea getStartArea();

    List<String> getAllowedModes();

    boolean allowMode(String name);

    void addAllowedMode(String name);

    void removeAllowedMode(String name);

    Set<Map.Entry<String, String>> optionsEntries();

    /**
     * Gets a new {@link GameOptions} object by merging this arena
     * options with the given options.
     * <p/>
     * In case of conflict, the priority is given to the argument options.
     * (ie. options of argument will override options of this arenas). 
     */
    GameOptions mergeOptions(GameOptions options);

    /**
     * Add an option to this arena
     * <p/>
     * If the option already exists, it will be overwritten.
     */
    void setOption(String name, String value);

    /**
     * Removes an option to this arena
     * <p/>
     * If the option doesn't exists, this does nothing.
     */
    void removeOption(String name);

}
