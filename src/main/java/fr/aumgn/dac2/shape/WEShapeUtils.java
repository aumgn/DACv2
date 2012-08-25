package fr.aumgn.dac2.shape;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CylinderRegion;
import com.sk89q.worldedit.regions.EllipsoidRegion;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.CuboidRegion;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.exceptions.WERegionNotSupported;
import fr.aumgn.dac2.exceptions.WESelectionNotSupported;
import fr.aumgn.dac2.utils.CylinderSelection;
import fr.aumgn.dac2.utils.EllipsoidSelection;

public class WEShapeUtils {

    public static Shape getShape(DAC dac, Region region) {
        if (region instanceof CuboidRegion) {
            CuboidRegion cuboid = (CuboidRegion) region;
            return new CuboidShape(we2bu(cuboid.getMinimumPoint()),
                    we2bu(cuboid.getMaximumPoint()));
        } else if (region instanceof Polygonal2DRegion) {
            Polygonal2DRegion poly = (Polygonal2DRegion) region;
            List<BlockVector2D> wePoints = poly.getPoints();
            Vector2D[] points = new Vector2D[wePoints.size()];
            int i = 0;
            for (BlockVector2D wePoint : wePoints) {
                points[i] = we2bu(wePoint);
                i++;
            }
            return new PolygonalShape(poly.getMinimumY(), poly.getMaximumY(),
                    points);
        } else if (region instanceof CylinderRegion) {
            CylinderRegion cyl = (CylinderRegion) region;
            return new CylinderShape(we2bu(cyl.getCenter().toVector2D()),
                    we2bu(cyl.getRadius()), cyl.getMinimumY(),
                    cyl.getMaximumY());
        } else if (region instanceof EllipsoidRegion) {
            EllipsoidRegion ellipsoid = (EllipsoidRegion) region;
            return new EllipsoidShape(we2bu(ellipsoid.getCenter()),
                    we2bu(ellipsoid.getRadius()));
        } else {
            throw new WERegionNotSupported(dac, region.getClass());
        }
    }

    public static Selection getSelection(DAC dac, World world, Shape shape) {
        if (shape instanceof CuboidShape) {
            CuboidShape cuboid = (CuboidShape) shape;
            return new CuboidSelection(world, bu2we(cuboid.getMin()),
                    bu2we(cuboid.getMax()));
        } else if (shape instanceof PolygonalShape) {
            PolygonalShape poly = (PolygonalShape) shape;
            List<BlockVector2D> wePoints = new ArrayList<BlockVector2D>();
            for (Vector2D pt : poly.getPoints()) {
                wePoints.add(bu2blockwe(pt));
            }
            return new Polygonal2DSelection(world, wePoints,
                    (int) poly.getMinY(), (int) poly.getMaxY());
        } else if (shape instanceof CylinderShape) {
            CylinderShape cyl = (CylinderShape) shape;
            return new CylinderSelection(world, bu2we(cyl.getCenter()),
                    bu2we(cyl.getRadius()), (int) cyl.getMinY(),
                    (int) cyl.getMaxY()); 
        } else if (shape instanceof EllipsoidShape) {
            EllipsoidShape ellipsoid = (EllipsoidShape) shape;
            return new EllipsoidSelection(world,
                    bu2we(ellipsoid.getCenter()),
                    bu2we(ellipsoid.getRadius()));
        } else {
            throw new WESelectionNotSupported(dac, shape.getClass());
        }
    }

    private static Vector we2bu(com.sk89q.worldedit.Vector vector) {
        return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    private static com.sk89q.worldedit.Vector bu2we(Vector vector) {
        return new com.sk89q.worldedit.Vector(
                vector.getX(), vector.getY(), vector.getZ());
    }

    private static Vector2D we2bu(com.sk89q.worldedit.Vector2D vector) {
        return new Vector2D(vector.getX(), vector.getZ());
    }

    private static com.sk89q.worldedit.Vector2D bu2we(Vector2D vector) {
        return new com.sk89q.worldedit.Vector2D(vector.getX(), vector.getZ());
    }

    private static BlockVector2D bu2blockwe(Vector2D pt) {
        return new BlockVector2D(pt.getX(), pt.getZ());
    }

    private WEShapeUtils() {
    }
}
