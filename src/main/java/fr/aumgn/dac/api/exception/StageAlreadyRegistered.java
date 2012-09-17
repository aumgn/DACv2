package fr.aumgn.dac.api.exception;

/**
 * Thrown when trying to register an already registered stage.    
 */
public class StageAlreadyRegistered extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StageAlreadyRegistered() {
        super("Tried to register a stage which is already registered.");
    }

}
