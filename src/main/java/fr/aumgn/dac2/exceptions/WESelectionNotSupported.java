package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.shape.Shape;

public class WESelectionNotSupported extends DACException {

    private static final long serialVersionUID = 2964152704608363073L;

    public WESelectionNotSupported(DAC dac, Class<? extends Shape> clazz) {
        super(dac.getMessages().get(
                "worledit.selection.unsupported", clazz.getSimpleName()));
    }
}
