package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;

/**
 * Thrown when trying to access WorldEdit whereas it has been disabled.
 */
public class WorldEditNotAvailable extends DACException {

    public WorldEditNotAvailable(DAC dac) {
        super(dac.getMessages().get("worldedit.notavailable"));
    }
}
