package fr.aumgn.dac2.exceptions;

import fr.aumgn.bukkitutils.command.exception.CommandException;

public abstract class DACException extends RuntimeException implements CommandException {

    public DACException(String message) {
        super(message);
    }
}
