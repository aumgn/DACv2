package fr.aumgn.dac2.shape;

import java.util.Iterator;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.shape.iterator.ColumnsIterator;

@ShapeName("cylinder")
public class CylinderShape implements FlatShape {

    private final Vector2D center;
    private final Vector2D radius;
    private final int minY;
    private final int maxY;

    public CylinderShape(Vector2D center, Vector2D radius, int minY, int maxY) {
        this.center = center;
        this.radius = radius.add(0.5);
        this.minY = minY;
        this.maxY = maxY;
    }

    @Override
    public boolean contains(Vector pt) {
        return pt.getY() >= minY && pt.getY() <= maxY
                && pt.to2D().subtract(center).divide(radius).lengthSq() <= 1;
    }

    @Override
    public double getMinY() {
        return minY;
    }

    @Override
    public double getMaxY() {
        return maxY;
    }

    @Override
    public Vector2D getMin2D() {
        return center.subtract(radius.subtract(0.5));
    }

    @Override
    public Vector2D getMax2D() {
        return center.add(radius.subtract(0.5));
    }

    @Override
    public boolean contains2D(Vector2D pt) {
        return pt.subtract(center).divide(radius).lengthSq() <= 1;
    }

    @Override
    public Column getColumn(Vector2D pt) {
        return new Column(this, pt);
    }

    @Override
    public Iterator<Column> iterator() {
        return new ColumnsIterator(this);
    }

    public Vector2D getCenter() {
        return center;
    }

    public Vector2D getRadius() {
        return radius.subtract(0.5);
    }
}
