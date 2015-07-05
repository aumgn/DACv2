package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;

/**
 * Thrown when trying to assign a non flat shape to a pool.
 */
public class PoolShapeNotFlat extends DACException {

    public PoolShapeNotFlat(DAC dac, Object shape) {
        super(dac.getMessages().get("pool.shape.notflat",
                shape.getClass().getSimpleName()));
    }
}
