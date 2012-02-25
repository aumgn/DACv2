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
     * Same as {@link Stage#removePlayer(StagePlayer)} but specify the reason.
     * 
     * @param player the player to remove
     * @param reason the Quit reason
     * @return true if player was not watching game, false otherwise  
     */
    void removePlayer(StagePlayer player, StageQuitReason reason);

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
     * Stops the stage. 
     */
    void stop();

}
