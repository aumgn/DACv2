package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.shape.Shape;

/**
 * Thrown when trying to create a selector from a shape which is not supported.
 * Can be because the shape does not have a corresponding region or
 * because the specified selector selector doesn't support this shape. 
 */
public class WESelectionNotSupported extends DACException {

    private static final long serialVersionUID = 2964152704608363073L;

    public WESelectionNotSupported(DAC dac, Class<? extends Shape> clazz) {
        super(dac.getMessages().get(
                "worldedit.selection.unsupported", clazz.getSimpleName()));
    }
}
