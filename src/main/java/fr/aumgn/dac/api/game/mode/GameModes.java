package fr.aumgn.dac.api.game.mode;

import java.util.Collection;
import java.util.Set;

/**
 * Class responsible for managing registered {@link GameMode}.
 */
public interface GameModes {

    /**
     * Gets the game modes which are flagged as default.
     * @see DACGameMode#isDefault()
     * 
     * @return a set of all default game modes
     */
    Set<String> getDefaults();

    /**
     * Gets a collection of all available game modes names.
     * 
     * @return a collection of the available game modes names.
     */
    Collection<String> getNames();

    /**
     * Gets the {@link GameMode} for the given name.  
     * 
     * @param name the name of the {@link GameMode} 
     * @return the GameMode for this name
     */
    GameMode<?> get(String name);

}
