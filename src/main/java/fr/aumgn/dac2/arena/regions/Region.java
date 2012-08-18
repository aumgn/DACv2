package fr.aumgn.dac2.arena.regions;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.shape.Shape;

public abstract class Region {

    public abstract Shape getShape();

    public boolean contains(Vector pt) {
        return getShape().contains(pt);
    }
}
