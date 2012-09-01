package fr.aumgn.dac2.exceptions;

/**
 * Thrown when the creation of the root directory
 * of all .json arenas files failed.
 */
public class ArenasFolderException extends DACException {

    private static final long serialVersionUID = -5787336206129989817L;

    public ArenasFolderException(String message) {
        super(message);
    }
}
