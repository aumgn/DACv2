package fr.aumgn.dac2.shape;

import fr.aumgn.bukkitutils.geom.Vector;

@ShapeName("ellipsoid")
public class EllipsoidShape implements Shape {

    private final Vector center;
    private final Vector radius;

    public EllipsoidShape(Vector center, Vector radius) {
        this.center = center;
        this.radius = radius.add(0.5);
    }

    @Override
    public boolean contains(Vector pt) {
        return pt.subtract(center).divide(radius).lengthSq() <= 1;
    }

    @Override
    public Vector getMin() {
        return center.subtract(radius.subtract(0.5));
    }

    @Override
    public Vector getMax() {
        return center.add(radius.subtract(0.5));
    }

    public Vector getCenter() {
        return center;
    }

    public Vector getRadius() {
        return radius.subtract(0.5);
    }
}
