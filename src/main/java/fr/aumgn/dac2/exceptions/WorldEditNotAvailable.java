package fr.aumgn.dac2.exceptions;

import fr.aumgn.bukkitutils.command.exception.CommandException;
import fr.aumgn.dac2.DAC;

public class WorldEditNotAvailable extends RuntimeException
        implements CommandException {

    private static final long serialVersionUID = -7856116778156672898L;

    public WorldEditNotAvailable(DAC dac) {
        super(dac.getMessages().get("worldedit.notavailable"));
    }
}
