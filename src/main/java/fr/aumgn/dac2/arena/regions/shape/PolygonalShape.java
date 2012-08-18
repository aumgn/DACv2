package fr.aumgn.dac2.arena.regions.shape;

import static fr.aumgn.dac2.utils.WEUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
import com.sk89q.worldedit.regions.Polygonal2DRegion;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;

@ShapeName("polygonal")
public class PolygonalShape implements FlatShape {

    private final int minY;
    private final int maxY;
    private final Vector2D[] points;

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

    /**
     * Extracted (and slightly modified) from WorldEdit.
     * Credits goes to sk89q.
     */
    @Override
    public boolean contains(Vector pt) {
        if (points.length < 3) {
            return false;
        }
        int targetX = pt.getBlockX(); //wide
        int targetY = pt.getBlockY(); //height
        int targetZ = pt.getBlockZ(); //depth

        if (targetY < minY || targetY > maxY) {
            return false;
        }

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
    public Polygonal2DSelection getSelection(World world) {
        List<BlockVector2D> wePoints = new ArrayList<BlockVector2D>();
        for (Vector2D pt : points) {
            wePoints.add(bu2blockwe(pt));
        }
        return new Polygonal2DSelection(world, wePoints, minY, maxY);
    }
}
