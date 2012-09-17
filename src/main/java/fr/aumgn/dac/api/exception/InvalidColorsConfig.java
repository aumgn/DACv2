package fr.aumgn.dac.api.exception;

/**
 * Thrown when parsing an invalid colors configuration.
 */
public class InvalidColorsConfig extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidColorsConfig() {
        super("Invalid colors configuration.");
    }

}