package fr.aumgn.dac2.exceptions;

/**
 * Thrown when the removal of an arenas .json file failed.
 */
public class ArenaDeleteException extends DACException {

    private static final long serialVersionUID = -9213183276562854593L;

    public ArenaDeleteException(String message) {
        super(message);
    }
}
