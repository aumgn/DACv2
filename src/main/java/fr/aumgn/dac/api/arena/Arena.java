package fr.aumgn.dac.api.arena;

import java.util.List;
import java.util.Map;

import org.bukkit.World;

import com.sk89q.worldedit.LocalWorld;

import fr.aumgn.dac.api.game.GameOptions;

/**
 * Represents a DAC arena.
 */
public interface Arena {

    /**
     * Gets the name of this arena.
     * 
     * @return the name of this arena
     */
    String getName();

    /**
     * Gets the world this arena is in.
     * 
     * @return the world of this arena
     */
    World getWorld();
    
    /**
     * Gets the WorldEdit world wrapper.
     * @see #getWorld()
     */
    LocalWorld getWEWorld();

    /**
     * Gets the {@link DivingBoard} of this arena.
     * 
     * @return the diving board
     */
    DivingBoard getDivingBoard();

    /**
     * Gets the {@link Pool} of this arena.
     * 
     * @return the pool
     */
    Pool getPool();

    /**
     * Gets the {@link StartArea} of this arena.
     * 
     * @return the start area
     */
    StartArea getStartArea();

    /**
     * Gets the {@link fr.aumgn.dac.api.game.mode#GameMode} names allowed for this arena.
     * 
     * @return a list of name of the game modes allowed in this arena  
     */
    List<String> getModes();

    /**
     * Checks if this allows the {@link fr.aumgn.dac.api.game.mode#GameMode} represented by this name.
     *  
     * @param name the name of the {@link fr.aumgn.dac.api.game.mode#GameMode} to check
     * @return whether or not this {@link fr.aumgn.dac.api.game.mode#GameMode} is allowed
     */
    boolean hasMode(String name);

    /**
     * Adds this name as a game mode allowed. 
     *  
     * @param name the name of the {@link fr.aumgn.dac.api.game.mode#GameMode} to add
     */
    void addMode(String name);

    /**
     * Removes this name as a game mode allowed. 
     *  
     * @param name the name of the {@link fr.aumgn.dac.api.game.mode#GameMode} to remove
     */
    void removeMode(String name);
    
    /** 
     * Gets an iterator over the options associated with this arena.
     * The options are represented as a Map.Entry with option name as
     * the key and option value as the ... value.
     *   
     * @return an iterator of the available options 
     */
    Iterable<Map.Entry<String, String>> options();

    /**
     * Gets a new {@link GameOptions} object by merging this arena
     * options with the given options.
     * <p/>
     * In case of conflict, the priority is given to the argument options.
     * (ie. options of argument will override options of this arenas). 
     *  
     * @param options the options to merge into
     * @return the new options after merge
     */
    GameOptions mergeOptions(GameOptions options);

    /**
     * Add an option to this arena
     * <p/>
     * If the option already exists, it will be overwritten.
     * 
     * @param name the name of the option to add
     * @param value the value of the option to add
     */
    void setOption(String name, String value);

    /**
     * Removes an option to this arena
     * <p/>
     * If the option doesn't exists, this does nothing.
     * 
     * @param name the name of the option to remove
     */
    void removeOption(String name);

}
