package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.shape.Shape;

public class PoolShapeNotFlat extends DACException {

    private static final long serialVersionUID = 6261314447607981035L;

    public PoolShapeNotFlat(DAC dac, Shape shape) {
        super(dac.getMessages().get("pool.shape.notflat",
                shape.getClass().getSimpleName()));
    }
}
