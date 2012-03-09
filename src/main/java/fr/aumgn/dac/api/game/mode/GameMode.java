package fr.aumgn.dac.api.game.mode;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;

/**
 * Game factory class which represents a game mode.
 */
public interface GameMode {

    Game createGame(Stage stage, GameOptions options);

    /**
     * Creates the inherited instance of {@link StagePlayer} associated with this game mode.
     */
    StagePlayer createPlayer(Game game, StagePlayer player);

}
