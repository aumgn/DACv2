package fr.aumgn.dac.api.stage;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.dac.api.config.DACColor;
import fr.aumgn.dac.api.util.PlayerTeleporter;

/**
 * Player wrapper for a bukkit player which is in a stage.
 */
public interface StagePlayer {

    Stage getStage();

    Player getPlayer();

    /**
     * Gets the display name of this player used in stages.
     * It's may be different from the bukkit display name.
     */
    String getDisplayName();

    DACColor getColor();

    Location getStartLocation();

    void send(Object message);

    String formatForList();

    PlayerTeleporter teleporter();

}

