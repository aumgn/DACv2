package fr.aumgn.dac.api.game;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;

/**
 * Represents a running dé a coudre game. 
 * 
 * @param <T> the subclass of {@link StagePlayer} used by this game
 */
public interface Game<T extends StagePlayer> extends Stage<T> {

    /**
     * Gets the {@link GameMode} for this game.
     * 
     * @return the game mode of this game
     */
    GameMode<T> getMode();

    /**
     * Changes turn to the next player.
     */
    void nextTurn();

    /**
     * Checks if it's player turn.
     * 
     * @param player the player to checks.
     * @return whether or not it's the player turn.
     */
    boolean isPlayerTurn(T player);

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

    /**
     * Gets the {@link GameOptions} of this game.
     * 
     * @return the gameOptions
     */
    GameOptions getOptions();

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
     * Gets the parsed propulsion game option.
     * 
     * @return the parsed propulsion Vector
     */
    Vector getPropulsion();

    /**
     * Gets the parsed propulsion delay option.
     * 
     * @return the parsed propulsion delay
     */
    int getPropulsionDelay();

}