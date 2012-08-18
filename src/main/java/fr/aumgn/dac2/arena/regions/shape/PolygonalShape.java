package fr.aumgn.dac2.arena.regions.shape;

import static fr.aumgn.dac2.utils.WEUtils.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.World;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
import com.sk89q.worldedit.regions.Polygonal2DRegion;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.arena.regions.shape.iterator.ColumnsIterator;

@ShapeName("polygonal")
public class PolygonalShape implements FlatShape {

    private final int minY;
    private final int maxY;
    private final Vector2D[] points;

    private transient Vector2D min2D = null;
    private transient Vector2D max2D = null;

    public PolygonalShape(Polygonal2DRegion region) {
        this.minY = region.getMinimumY();
        this.maxY = region.getMaximumY();
        List<BlockVector2D> wePoints = region.getPoints();
        this.points = new Vector2D[wePoints.size()];
        int i = 0;
        for (BlockVector2D wePoint : wePoints) {
            points[i] = we2bu(wePoint);
            i++;
        }
    }

    @Override
    public boolean contains(Vector pt) {
        int targetY = pt.getBlockY();
        return targetY >= minY && targetY <= maxY && contains2D(pt.to2D());
    }

    @Override
    public Polygonal2DSelection getSelection(World world) {
        List<BlockVector2D> wePoints = new ArrayList<BlockVector2D>();
        for (Vector2D pt : points) {
            wePoints.add(bu2blockwe(pt));
        }
        return new Polygonal2DSelection(world, wePoints, minY, maxY);
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

    /*
     * Extracted (and slightly adapted) from WorldEdit.
     * Credits goes to sk89q.
     */
    @Override
    public boolean contains2D(Vector2D pt) {
        if (points.length < 3) {
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
    public Iterator<Column> iterator() {
        return new ColumnsIterator(this);
    }
}
