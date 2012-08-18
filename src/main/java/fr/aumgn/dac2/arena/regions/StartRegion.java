package fr.aumgn.dac2.arena.regions;

import fr.aumgn.dac2.arena.regions.shape.Shape;

public class StartRegion extends Region {

    private final Shape shape;

    public StartRegion(Shape shape) {
        this.shape = shape;
    }

    @Override
    public Shape getShape() {
        return shape;
    }
}
