package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.shape.Shape;
import fr.aumgn.dac2.shape.worldedit.WESelector;

/**
 * Thrown when trying to select a shape with an invalid worldedit selector.
 */
public class InvalidSelectorForRegion extends DACException {

    public InvalidSelectorForRegion(DAC dac, WESelector selector,
                                    Class<? extends Shape> clazz) {
        super(dac.getMessages().get("worldedit.selector.invalid",
                selector.name(), clazz.getSimpleName()));
    }
}
