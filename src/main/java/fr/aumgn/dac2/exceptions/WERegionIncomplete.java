package fr.aumgn.dac2.exceptions;

import fr.aumgn.bukkitutils.command.exception.CommandException;
import fr.aumgn.dac2.DAC;

public class WERegionIncomplete extends RuntimeException
        implements CommandException {

    private static final long serialVersionUID = 810428822681281912L;

    public WERegionIncomplete(DAC dac) {
        super(dac.getMessages().get("worldedit.region.incomplete"));
    }
}
