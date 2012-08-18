package fr.aumgn.dac2.arena.regions;

import com.sk89q.worldedit.regions.CylinderRegion;
import com.sk89q.worldedit.regions.EllipsoidRegion;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.CuboidRegion;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.regions.shape.CuboidShape;
import fr.aumgn.dac2.arena.regions.shape.CylinderShape;
import fr.aumgn.dac2.arena.regions.shape.EllipsoidShape;
import fr.aumgn.dac2.arena.regions.shape.FlatShape;
import fr.aumgn.dac2.arena.regions.shape.PolygonalShape;
import fr.aumgn.dac2.arena.regions.shape.Shape;
import fr.aumgn.dac2.exceptions.PoolShapeNotFlat;
import fr.aumgn.dac2.exceptions.WERegionNotSupported;

public class WERegionFactory {

    public static Pool createPool(DAC dac, Region weRegion) {
        Shape shape = create(dac, weRegion);
        if (!(shape instanceof FlatShape)) {
            throw new PoolShapeNotFlat(dac, shape);
        }

        return new Pool((FlatShape) shape);
    }

    public static StartRegion createStartRegion(DAC dac, Region region) {
        return new StartRegion(create(dac, region));
    }

    private static Shape create(DAC dac, Region region) {
        if (region instanceof CuboidRegion) {
            return new CuboidShape((CuboidRegion) region);
        } else if (region instanceof Polygonal2DRegion) {
            return new PolygonalShape((Polygonal2DRegion) region);
        } else if (region instanceof CylinderRegion) {
            return new CylinderShape((CylinderRegion) region);
        } else if (region instanceof EllipsoidRegion) {
            return new EllipsoidShape((EllipsoidRegion) region);
        } else {
            throw new WERegionNotSupported(dac, region.getClass());
        }
    }

    private WERegionFactory() {
    }
}
