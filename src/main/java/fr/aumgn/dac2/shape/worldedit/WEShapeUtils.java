package fr.aumgn.dac2.shape.worldedit;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.bukkit.BukkitWorld;
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
            return new CuboidShape(worldedit2bukkit(cuboid.getMinimumPoint()),
                    worldedit2bukkit(cuboid.getMaximumPoint()));
        }
        else if (region instanceof Polygonal2DRegion) {
            Polygonal2DRegion poly = (Polygonal2DRegion) region;
            List<BlockVector2D> wePoints = poly.getPoints();
            Vector2D[] points = new Vector2D[wePoints.size()];
            int i = 0;
            for (BlockVector2D wePoint : wePoints) {
                points[i] = worldedit2bukkit(wePoint);
                i++;
            }
            return new PolygonalShape(poly.getMinimumY(), poly.getMaximumY(),
                    points);
        }
        else if (region instanceof CylinderRegion) {
            CylinderRegion cyl = (CylinderRegion) region;
            return new CylinderShape(worldedit2bukkit(cyl.getCenter().toVector2D()),
                    worldedit2bukkit(cyl.getRadius()), cyl.getMinimumY(),
                    cyl.getMaximumY());
        }
        else if (region instanceof EllipsoidRegion) {
            EllipsoidRegion ellipsoid = (EllipsoidRegion) region;
            return new EllipsoidShape(worldedit2bukkit(ellipsoid.getCenter()),
                    worldedit2bukkit(ellipsoid.getRadius()));
        }
        else {
            throw new WERegionNotSupported(dac, region.getClass());
        }
    }

    public static Vector worldedit2bukkit(com.sk89q.worldedit.Vector vector) {
        return new Vector(vector.getX(), vector.getY(), vector.getZ());
    }

    public static com.sk89q.worldedit.Vector bukkit2worldedit(Vector vector) {
        return new com.sk89q.worldedit.Vector(
                vector.getX(), vector.getY(), vector.getZ());
    }

    public static Vector2D worldedit2bukkit(com.sk89q.worldedit.Vector2D vector) {
        return new Vector2D(vector.getX(), vector.getZ());
    }

    public static com.sk89q.worldedit.Vector2D bukkit2worldedit(Vector2D vector) {
        return new com.sk89q.worldedit.Vector2D(vector.getX(), vector.getZ());
    }

    public static BlockVector2D bukkit2blockWorldedit(Vector2D pt) {
        return new BlockVector2D(pt.getX(), pt.getZ());
    }

    public static com.sk89q.worldedit.world.World bukkit2worldedit(org.bukkit.World world) {
        return new BukkitWorld(world);
    }
}
