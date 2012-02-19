package fr.aumgn.dac.api.game.mode;

import fr.aumgn.dac.api.stage.StagePlayer;

/**
 * A class which is responsible for handling specifics 
 * behavior of the game mode
 * 
 * @param <T> the subclass of {@link StagePlayer} used by the game mode 
 */
public interface GameModeHandler<T extends StagePlayer> {

    /**
     * Called when a new game start.
     */
    void onStart();

    /**
     * Called when a new turn starts.
     * (ie. when all players have played their turn) 
     */
    void onNewTurn();

    /**
     * Called when a player turn starts.
     * 
     * @param player the player
     */
    void onTurn(T player);

    /**
     * Called when a player succeeds.
     * 
     * @param player the player
     */
    void onSuccess(T player);

    /**
     * Called when a player fails.
     * 
     * @param player the player
     */
    void onFail(T player);

    /**
     * Called when a player quit the game.
     * 
     * @param player the player
     */
    void onQuit(T player);

    /**
     * Called when the game stop.
     */
    void onStop();

}
