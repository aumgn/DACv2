package fr.aumgn.dac2.arena.regions;

import fr.aumgn.dac2.arena.regions.shape.FlatShape;
import fr.aumgn.dac2.arena.regions.shape.Shape;

public class Pool extends Region {

    private final FlatShape shape;

    // Constructor used with reflection
    @SuppressWarnings("unused")
    private Pool(Shape shape) {
        this((FlatShape) shape);
    }

    public Pool(FlatShape shape) {
        this.shape = shape;
    }

    @Override
    public Shape getShape() {
        return shape;
    }
}
