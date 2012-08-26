package fr.aumgn.dac2.shape.column;

import fr.aumgn.dac2.config.Color;

public class ColorPattern extends BasicPattern {

    public ColorPattern(Color color) {
        super(color.block, color.data);
    }
}
