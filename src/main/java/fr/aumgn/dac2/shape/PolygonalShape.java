package fr.aumgn.dac2.shape;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.shape.column.Column;
import fr.aumgn.dac2.shape.iterator.ColumnsIterator;

@ShapeName("polygonal")
public class PolygonalShape implements FlatShape {

    private final int minY;
    private final int maxY;
    private final Vector2D[] points;

    private transient Vector2D min2D = null;
    private transient Vector2D max2D = null;

    public PolygonalShape(int minY, int maxY, Vector2D[] points) {
        this.minY = minY;
        this.maxY = maxY;
        this.points = points;
    }

    @Override
    public boolean contains(Vector pt) {
        int targetY = pt.getBlockY();
        return targetY >= minY && targetY <= maxY && contains2D(pt.to2D());
    }

    @Override
    public Vector getMin() {
        calculateMinMax2D();
        return min2D.to3D(minY);
    }

    @Override
    public Vector getMax() {
        calculateMinMax2D();
        return max2D.to3D(maxY);
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
        calculateMinMax2D();
        return min2D;
    }

    @Override
    public Vector2D getMax2D() {
        calculateMinMax2D();
        return max2D;
    }

    private void calculateMinMax2D() {
        if (min2D != null) {
            return;
        }

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

    /*
     * Extracted (and slightly adapted) from WorldEdit.
     * Credits goes to sk89q.
     */
    @Override
    public boolean contains2D(Vector2D pt) {
        if (points.length < 3) {
            return false;
        }

        calculateMinMax2D();
        if (!pt.isInside(min2D, max2D)) {
            return false;
        }

        int targetX = pt.getBlockX(); //wide
        int targetZ = pt.getBlockZ(); //depth

        boolean inside = false;
        int npoints = points.length;
        int xNew, zNew;
        int xOld, zOld;
        int x1, z1;
        int x2, z2;
        long crossproduct;
        int i;

        xOld = points[npoints - 1].getBlockX();
        zOld = points[npoints - 1].getBlockZ();

        for (i = 0; i < npoints; ++i) {
            xNew = points[i].getBlockX();
            zNew = points[i].getBlockZ();
            //Check for corner
            if (xNew == targetX && zNew == targetZ) {
                return true;
            }
            if (xNew > xOld) {
                x1 = xOld;
                x2 = xNew;
                z1 = zOld;
                z2 = zNew;
            } else {
                x1 = xNew;
                x2 = xOld;
                z1 = zNew;
                z2 = zOld;
            }
            if (x1 <= targetX && targetX <= x2) {
                crossproduct = ((long) targetZ - (long) z1) * (long) (x2 - x1)
                        - ((long) z2 - (long) z1) * (long) (targetX - x1);
                if (crossproduct == 0) {
                    if ((z1 <= targetZ) == (targetZ <= z2)) return true; //on edge
                } else if (crossproduct < 0 && (x1 != targetX)) {
                    inside = !inside;
                }
            }
            xOld = xNew;
            zOld = zNew;
        }

        return inside;
    }

    @Override
    public Column getColumn(Vector2D pt) {
        return new Column(this, pt);
    }

    @Override
    public Iterator<Column> iterator() {
        return new ColumnsIterator(this);
    }

    public List<Vector2D> getPoints() {
        return Arrays.<Vector2D>asList(points);
    }
}
