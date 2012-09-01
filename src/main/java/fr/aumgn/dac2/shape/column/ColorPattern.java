package fr.aumgn.dac2.shape.column;

import fr.aumgn.dac2.config.Color;

/**
 * Convenient class to construct a UniformColumnPattern based
 * on a {@link Color}.
 */
public class ColorPattern extends UniformPattern {

    public ColorPattern(Color color) {
        super(color.block, color.data);
    }
}
