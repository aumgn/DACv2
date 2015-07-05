package fr.aumgn.dac2.exceptions;

/**
 * Thrown when the removal of an arenas .json file failed.
 */
public class ArenaDeleteException extends DACException {

    public ArenaDeleteException(String message) {
        super(message);
    }
}
