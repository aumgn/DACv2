package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;

/**
 * Thrown when trying to use an incomplete WorldEdit Region.
 */
public class WERegionIncomplete extends DACException {

    public WERegionIncomplete(DAC dac) {
        super(dac.getMessages().get("worldedit.region.incomplete"));
    }
}
