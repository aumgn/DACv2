package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;

/**
 * Thrown when trying to define an arbitrary shape which is too large.
 */
public class TooLargeArbitraryShape extends DACException {

    public TooLargeArbitraryShape(DAC dac) {
        super(dac.getMessages().get("arbitraryshape.toolarge"));
    }
}
