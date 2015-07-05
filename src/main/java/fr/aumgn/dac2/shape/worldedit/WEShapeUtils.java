package fr.aumgn.dac2.shape.worldedit;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.regions.*;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.exceptions.WERegionNotSupported;
import fr.aumgn.dac2.shape.*;

import java.util.List;

public class WEShapeUtils {

    private WEShapeUtils() {
    }

    public static Shape getShape(DAC dac, Region region) {
        if (region instanceof CuboidRegion) {
            CuboidRegion cuboid = (CuboidRegion) region;
            return new CuboidShape(we2bu(cuboid.getMinimumPoint()),
                    we2bu(cuboid.getMaximumPoint()));
        }
        else if (region instanceof Polygonal2DRegion) {
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
        }
        else if (region instanceof CylinderRegion) {
            CylinderRegion cyl = (CylinderRegion) region;
            return new CylinderShape(we2bu(cyl.getCenter().toVector2D()),
                    we2bu(cyl.getRadius()), cyl.getMinimumY(),
                    cyl.getMaximumY());
        }
        else if (region instanceof EllipsoidRegion) {
            EllipsoidRegion ellipsoid = (EllipsoidRegion) region;
            return new EllipsoidShape(we2bu(ellipsoid.getCenter()),
                    we2bu(ellipsoid.getRadius()));
        }
        else {
            throw new WERegionNotSupported(dac, region.getClass());
        }
    }

    public static Vector we2bu(com.sk89q.worldedit.Vector vector) {
        return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    public static com.sk89q.worldedit.Vector bu2we(Vector vector) {
        return new com.sk89q.worldedit.Vector(
                vector.getX(), vector.getY(), vector.getZ());
    }

    public static Vector2D we2bu(com.sk89q.worldedit.Vector2D vector) {
        return new Vector2D(vector.getX(), vector.getZ());
    }

    public static com.sk89q.worldedit.Vector2D bu2we(Vector2D vector) {
        return new com.sk89q.worldedit.Vector2D(vector.getX(), vector.getZ());
    }

    public static BlockVector2D bu2blockwe(Vector2D pt) {
        return new BlockVector2D(pt.getX(), pt.getZ());
    }
}
