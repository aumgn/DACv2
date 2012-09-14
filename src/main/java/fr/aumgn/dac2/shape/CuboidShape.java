package fr.aumgn.dac2.shape;

import java.util.Iterator;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.shape.column.Column;
import fr.aumgn.dac2.shape.iterator.CuboidColumnsIterator;

@ShapeName("cuboid")
public class CuboidShape implements FlatShape {

    private final Vector min;
    private final Vector max;

    public CuboidShape(Vector min, Vector max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean contains(Vector pt) {
        return pt.isInside(min, max);
    }

    @Override
    public double getMinY() {
        return min.getY();
    }

    @Override
    public double getMaxY() {
        return max.getY();
    }

    @Override
    public Vector2D getMin2D() {
        return min.to2D();
    }

    @Override
    public Vector2D getMax2D() {
        return max.to2D();
    }

    @Override
    public boolean contains2D(Vector2D pt) {
        return pt.isInside(min.to2D(), max.to2D());
    }

    @Override
    public Column getColumn(Vector2D pt) {
        return new Column(this, pt);
    }

    @Override
    public Iterator<Column> iterator() {
        return new CuboidColumnsIterator(this);
    }

    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return max;
    }
}
