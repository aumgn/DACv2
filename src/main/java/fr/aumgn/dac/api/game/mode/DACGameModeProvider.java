package fr.aumgn.dac.api.game.mode;

import java.util.List;

/**
 * Flag interface for class which provides {@link GameMode}.
 * If the subclass isn't a Bukkit registered plugin main 
 * class this will do nothing. 
 */
public interface DACGameModeProvider {

    /**
     * Gets the game modes provided.
     * 
     * @return a list of the game modes
     */
    List<Class<? extends GameMode<?>>> getGameModes();

}
