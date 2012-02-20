package fr.aumgn.dac.api.stage;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.dac.api.config.DACColor;

/**
 * Player wrapper for a bukkit player which is in a stage.
 */
public interface StagePlayer {

    /**
     * Gets the stage this player is in.
     * 
     * @return the stage this player is in
     */
    Stage<?> getStage();

    /**
     * Gets the wrapped player. 
     * 
     * @return the wrapped player
     */
    Player getPlayer();

    /**
     * Gets the display name of this player used in stages.
     * 
     * @return the display name of this player
     */
    String getDisplayName();

    /**
     * Gets the color for this player.
     * 
     * @return the color of this player
     */
    DACColor getColor();

    /**
     * Gets the start location for this player.
     * 
     * @return the start location for this player
     */
    Location getStartLocation();

    /**
     * Sends a message to this player.
     * 
     * @param message the message to send 
     */
    void send(Object message);

    /**
     * Sends a message to all players of this player's stage 
     * except himself.
     * 
     * @param message the message to send
     */
    void sendToOthers(Object message);

    /**
     * Gets the formatted name for players list.
     * 
     * @return the formatted name
     */
    String formatForList();

    /**
     * Teleports player to his start location. 
     */
    void tpToStart();

    /**
     * Teleports player after jump.
     */
    void tpAfterJump();

    /**
     * Teleports player after fail.
     */
    void tpAfterFail();

    /**
     * Teleports the player to the diving of his stage
     */
    void tpToDiving();

}

    