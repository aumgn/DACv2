package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.shape.Shape;
import fr.aumgn.dac2.shape.worldedit.WESelector;

public class InvalidSelectorForRegion extends DACException {

    private static final long serialVersionUID = 2990085737849379453L;

    public InvalidSelectorForRegion(DAC dac, WESelector selector,
            Class<? extends Shape> clazz) {
        super(dac.getMessages().get("worldedit.selector.invalid",
                selector.name(), clazz.getSimpleName()));
    }
}
