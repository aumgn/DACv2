package fr.aumgn.dac2.exceptions;

import fr.aumgn.bukkitutils.command.exception.CommandException;

public abstract class DACException extends RuntimeException
    implements CommandException {

    private static final long serialVersionUID = -5753888313045179326L;

    public DACException(String message) {
        super(message);
    }
}
