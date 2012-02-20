package fr.aumgn.dac.api.exception;

/**
 * Thrown when trying to cast player to an invalid type.
 */
public class PlayerCastException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PlayerCastException(Exception exception) {
        super("Unable to cast player to adequate type", exception);
    }

}
