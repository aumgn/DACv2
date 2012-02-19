package fr.aumgn.dac.api.joinstage;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;

/**
 * Represents a join stage.
 * 
 * @param <T> the subclass of {@link StagePlayer} used by this join stage
 */
public interface JoinStage<T extends StagePlayer> extends Stage<T> {

    /**
     * Adds a player to the join stage.
     * 
     * @param player the player to add
     * @param args arguments passed to the command
     */
    void addPlayer(Player player, String[] args);

    /**
     * Checks if the required amount of players has been reached.
     * 
     * @param mode the game mode to check with 
     * @return whether the required amount has been reached
     */
    boolean isMinReached(GameMode<?> mode);

    /**
     * Checks if the maximum amount of players has been reached.
     * This depends on the number of color available in {@link fr.aumgn.dac.api.config#DACColors}. 
     * 
     * @return whether the maximum amount has been reached
     */
    boolean isMaxReached();

}
