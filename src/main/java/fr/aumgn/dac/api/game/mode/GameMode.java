package fr.aumgn.dac.api.game.mode;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.event.GameFinish;
import fr.aumgn.dac.api.game.event.GameJumpFail;
import fr.aumgn.dac.api.game.event.GameJumpSuccess;
import fr.aumgn.dac.api.game.event.GameLoose;
import fr.aumgn.dac.api.game.event.GameNewTurn;
import fr.aumgn.dac.api.game.event.GamePoolFilled;
import fr.aumgn.dac.api.game.event.GameStart;
import fr.aumgn.dac.api.game.event.GameTurn;
import fr.aumgn.dac.api.stage.StagePlayer;

/**
 * A class responsible of handling specifics 
 * behavior of the game mode.
 */
public interface GameMode {

    /**
     * Creates the inherited instance of {@link StagePlayer} associated with this game mode.
     */
    StagePlayer createPlayer(Game game, StagePlayer player);

    /**
     * Called when a new game starts
     * (ie. just after the game has been initialized.)
     * <p/>
     * You should use this method to initialize your handler 
     * rather than the constructor.
     */
    void onStart(GameStart event);

    /**
     * Called when a new turn starts.
     * (ie. before the first player own turn) 
     */
    void onNewTurn(GameNewTurn event);

    /**
     * Called when a player turn starts.
     */
    void onTurn(GameTurn event);

    /**
     * Called when a player succeeds.
     */
    void onSuccess(GameJumpSuccess event);

    /**
     * Called when a player fails.
     */
    void onFail(GameJumpFail event);

    /**
     * Called when a player loose.
     * Can be because he simply loose or because he has 
     * for one reason or another quit the game.
     */
    void onLoose(GameLoose event);    

    /**
     * Called when a pool is full.
     * (ie. does not contain any column water)
     */
    void onPoolFilled(GamePoolFilled event);

    /**
     * Called when the game finishes.
     * Can be because a player won or because has been stopped. 
     */
    void onFinish(GameFinish event);

}
