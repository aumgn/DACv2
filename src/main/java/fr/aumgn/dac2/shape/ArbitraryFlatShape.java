package fr.aumgn.dac2.shape;

import java.util.Iterator;
import java.util.Set;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.shape.column.Column;

@ShapeName("arbitrary-flat")
public class ArbitraryFlatShape implements FlatShape {

    private final Set<Vector2D> points;
    private final double minY;
    private final double maxY;

    private transient Vector2D min2D;
    private transient Vector2D max2D;

    public ArbitraryFlatShape(Set<Vector2D> points, double minY, double maxY) {
        this.points = points;
        this.minY = minY;
        this.maxY = maxY;

        this.min2D = null;
        this.max2D = null;
    }

    @Override
    public boolean contains(Vector pt) {
        return pt.getY() >= minY && pt.getY() <= maxY
                && contains2D(pt.to2D());
    }

    @Override
    public Iterator<Column> iterator() {
        final Iterator<Vector2D> it = points.iterator();
        return new Iterator<Column>() {

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Column next() {
                Vector2D next = it.next();
                return new Column(ArbitraryFlatShape.this, next);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
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
        if (min2D == null) {
            calculateMinMax2D();
        }

        return min2D;
    }

    @Override
    public Vector2D getMax2D() {
        if (max2D == null) {
            calculateMinMax2D();
        }

        return max2D;
    }

    private void calculateMinMax2D() {
        double minX = Double.MAX_VALUE;
        double minZ = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double maxZ = -Double.MAX_VALUE;

        for (Vector2D pt : points) {
            if (minX > pt.getX()) {
                minX = pt.getX();
            }
            if (minZ > pt.getZ()) {
                minZ = pt.getZ();
            }
            if (maxX < pt.getX()) {
                maxX = pt.getX();
            }
            if (maxZ < pt.getZ()) {
                maxZ = pt.getZ();
            }
        }

        min2D = new Vector2D(minX, minZ);
        max2D = new Vector2D(maxX, maxZ);
    }

    @Override
    public boolean contains2D(Vector2D pt) {
        if (min2D == null) {
            calculateMinMax2D();
        }

        return pt.isInside(min2D, max2D) && points.contains(pt);
    }

    @Override
    public Column getColumn(Vector2D pt) {
        return new Column(this, pt);
    }
}
