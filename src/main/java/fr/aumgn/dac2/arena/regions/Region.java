package fr.aumgn.dac2.arena.regions;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.arena.regions.shape.Shape;

public abstract class Region {

    private Shape shape;

    public Region(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public boolean contains(Vector pt) {
        return shape.contains(pt);
    }
}
