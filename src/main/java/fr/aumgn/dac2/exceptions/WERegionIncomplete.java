package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;

public class WERegionIncomplete extends DACException {

    private static final long serialVersionUID = 810428822681281912L;

    public WERegionIncomplete(DAC dac) {
        super(dac.getMessages().get("worldedit.region.incomplete"));
    }
}
