package fr.aumgn.dac.api.game.mode;

import fr.aumgn.dac.api.game.event.GameFinish;
import fr.aumgn.dac.api.game.event.GameJumpFail;
import fr.aumgn.dac.api.game.event.GameJumpSuccess;
import fr.aumgn.dac.api.game.event.GameLoose;
import fr.aumgn.dac.api.game.event.GameNewTurn;
import fr.aumgn.dac.api.game.event.GamePoolFilled;
import fr.aumgn.dac.api.game.event.GameStart;
import fr.aumgn.dac.api.game.event.GameTurn;

/**
 * A class which is responsible for handling specifics 
 * behavior of the game mode.
 */
public interface GameHandler {

    /**
     * Called when a new game starts
     * (ie. just after the game has been initialized.)
     * <p/>
     * You should use this method to initialize your handler 
     * rather than the constructor.
     * 
     * @param start the start event
     */
    void onStart(GameStart start);

    /**
     * Called when a new turn starts.
     * (ie. before the first player own turn) 
     * 
     * @param newTurn the new turn event
     */
    void onNewTurn(GameNewTurn newTurn);

    /**
     * Called when a player turn starts.
     * 
     * @param turn the turn event
     */
    void onTurn(GameTurn turn);

    /**
     * Called when a player succeeds.
     * 
     * @param success the success event
     */
    void onSuccess(GameJumpSuccess success);

    /**
     * Called when a player fails.
     * 
     * @param fail the fail event
     */
    void onFail(GameJumpFail fail);

    /**
     * Called when a player loose.
     * Can be because he simply loose or because he has 
     * for one reason or another quit the game.
     *  
     * @param loose the loose event
     */
    void onLoose(GameLoose loose);    

    /**
     * Called when the game finishes.
     * Can be because a player won or because has been stopped. 
     * 
     * @param finish the finish event
     */
    void onFinish(GameFinish finish);

    /**
     * Called when a pool is full.
     * (ie. does not contain any column water)
     * 
     * @param filled the filled event.
     */
    void onPoolFilled(GamePoolFilled filled);

}
