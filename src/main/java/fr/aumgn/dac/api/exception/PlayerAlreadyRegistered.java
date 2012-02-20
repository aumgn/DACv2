package fr.aumgn.dac.api.exception;

/**
 * Thrown when trying to register an already registered player.    
 */
public class PlayerAlreadyRegistered extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PlayerAlreadyRegistered() {
        super("Tried to register a player which is already registered.");
    }

}
