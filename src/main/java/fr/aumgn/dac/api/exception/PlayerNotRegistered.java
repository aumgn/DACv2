package fr.aumgn.dac.api.exception;

/**
 * Thrown when trying to unregister a player which is not registered.    
 */
public class PlayerNotRegistered extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PlayerNotRegistered() {
        super("Tried to unregister a player which is not registered.");
    }

}
