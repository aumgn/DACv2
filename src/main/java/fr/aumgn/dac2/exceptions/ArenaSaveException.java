package fr.aumgn.dac2.exceptions;

/**
 * Thrown when the creation of a .json arena file failed.
 */
public class ArenaSaveException extends DACException {

    public ArenaSaveException(String message) {
        super(message);
    }
}
