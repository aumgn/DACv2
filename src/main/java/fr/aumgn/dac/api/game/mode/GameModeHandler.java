package fr.aumgn.dac.api.game.mode;

import fr.aumgn.dac.api.stage.StagePlayer;

/**
 * A class which is responsible for handling specifics 
 * behavior of the game mode.
 * 
 * @param <T> the subclass of {@link StagePlayer} used by this game mode 
 */
public interface GameModeHandler<T extends StagePlayer> {

    /**
     * Called when a new game starts
     * (ie. just after the game has been initialized.)
     * <p/>
     * You should use this method to initialize your handler 
     * rather than the constructor. 
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
     * (with 'dac quit' command or by being disconnected of the server.)
     *  
     * @param player the player
     */
    void onQuit(T player);

    /**
     * Called when the game stop.
     */
    void onStop();

}
