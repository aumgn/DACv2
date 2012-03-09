package fr.aumgn.dac.api.joinstage;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.Stage;

/**
 * Represents a join stage.
 */
public interface JoinStage extends Stage {

    /**
     * @param args arguments passed to the command
     */
    void addPlayer(Player player, String[] args);

    boolean isMinReached(GameMode mode);

    boolean isMaxReached();

}
