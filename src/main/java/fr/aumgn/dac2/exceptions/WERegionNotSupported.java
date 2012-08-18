package fr.aumgn.dac2.exceptions;

import com.sk89q.worldedit.regions.Region;

import fr.aumgn.bukkitutils.command.exception.CommandException;
import fr.aumgn.dac2.DAC;

public class WERegionNotSupported extends RuntimeException
        implements CommandException {

    private static final long serialVersionUID = 1498774297971531082L;

    public WERegionNotSupported(DAC dac,
            Class<? extends Region> clazz) {
        super(dac.getMessages().get(
                "worldedit.region.unsupported", clazz.getSimpleName()));
    }
}
