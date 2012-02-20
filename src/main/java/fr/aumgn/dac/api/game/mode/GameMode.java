package fr.aumgn.dac.api.game.mode;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.joinstage.JoinStage;
import fr.aumgn.dac.api.stage.StagePlayer;

/**
 * Game factory class which represents a game mode.
 * 
 * @param <T> the subclass of {@link StagePlayer} used by this mode 
 */
public interface GameMode<T extends StagePlayer> {

    /**
     * Creates a new game with the given join stage and options.
     * 
     * @param joinStage the joinStage to create the new game with
     * @param options the options to use in the new game
     * @return the created game
     */
    Game<T> createGame(JoinStage<?> joinStage, GameOptions options);

    /**
     * Creates the {@link GameModeHandler} for this game mode.
     * 
     * @param game the game for which to create the handler 
     * @return the created handler
     */
    GameModeHandler<T> createHandler(Game<T> game);

    /**
     * Creates the inherited instance of {@link StagePlayer} associated with this game mode.
     * 
     * @param game the game for which to create the player
     * @param player the stage player to base the player upon
     * @param index the index of the player in the game  
     * @return the created inherited instance of StagePlayer
     */
    T createPlayer(Game<T> game, StagePlayer player, int index);

}
