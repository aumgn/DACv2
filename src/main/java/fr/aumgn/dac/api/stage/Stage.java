package fr.aumgn.dac.api.stage;

import java.util.List;

import fr.aumgn.dac.api.arena.Arena;

/**
 * Represents a stage
 * 
 * @param <T> the subclass of {@link StagePlayer} used by this stage
 */
public interface Stage {

    /**
     * Gets the arena in which this stage takes place.
     * 
     * @return the arena in which this stage takes place
     */
    Arena getArena();

    /**
     * Removes the player from the stage.
     * 
     * @param player the player to remove 
     */
    void removePlayer(StagePlayer player);

    /**
     * Gets players in this stage. 
     * 
     * @return a list of players in this stage  
     */
    List<StagePlayer> getPlayers();

    /**
     * Registers all players in {@link StagePlayerManager}.
     */
    void registerAll();

    /**
     * Registers all players from {@link StagePlayerManager}.
     */
    void unregisterAll();

    /**
     * Broadcasts a message to all players in this stage.
     * 
     * @param message the message to send
     */
    void send(Object message);

    /**
     * Broadcasts a message to all players in this stage except the given one.
     * 
     * @param message the message to send
     * @param exclude the player to exclude 
     */
    void send(Object message, StagePlayer exclude);

    /**
     * Stops the stage. 
     */
    void stop();

}
