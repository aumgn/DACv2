package fr.aumgn.dac.api.exception;

import fr.aumgn.bukkitutils.command.exception.CommandException;
import fr.aumgn.dac.api.config.DACMessage;

public class DACException extends RuntimeException implements CommandException {

    public DACException(String message) {
        super(message);
    }

    public DACException(DACMessage message) {
        this(message.getContent());
    }
}
