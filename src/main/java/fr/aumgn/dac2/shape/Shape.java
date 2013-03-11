package fr.aumgn.dac2.shape;

import fr.aumgn.bukkitutils.geom.Vector;

/**
 * Represents a region shape.
 */
public interface Shape {

    boolean contains(Vector pt);

    Vector getMin();

    Vector getMax();
}
