package fr.aumgn.dac.api.game;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.Stage;

/**
 * Represents a running dé a coudre game. 
 */
public interface Game extends Stage {

    /**
     * Gets the {@link GameMode} for this game.
     * 
     * @return the game mode of this game
     */
    GameMode getMode();

    /**
     * Gets the {@link GameOptions} of this game.
     * 
     * @return the gameOptions
     */
    GameOptions getOptions();

    /**
     * Indicates if a player can watch a game.
     * 
     * @param player the player
     * @return true if it can, false otherwise
     */
    public boolean canWatch(Player player);

    /**
     * Adds a spectator to this game.
     * <p/>
     * Returns false if player was already watching game, true otherwise.
     * 
     * @param player the player to add as a spectator
     * @return false if player was already watching game, true otherwise  
     */
    boolean addSpectator(Player player);

    /**
     * Removes a spectator to this game.
     * <p/>
     * Returns true if player was not watching game, false otherwise.
     * 
     * @param player the player to remove as a spectator
     * @return true if player was not watching game, false otherwise  
     */
    boolean removeSpectator(Player player);
    
    /**
     * Sends the given message to all spectators prefixed with the arena's name.
     */
    void sendToSpectators(String message);

    /**
     * Handles fall damage event.
     * 
     * @param event the EntityDamageEvent
     */
    void onFallDamage(EntityDamageEvent event);

    /**
     * Handles move event.
     * 
     * @param event the PlayerMoveEvent
     */
    void onMove(PlayerMoveEvent event);

}