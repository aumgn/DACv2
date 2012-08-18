package fr.aumgn.dac2.arena.regions;

import com.sk89q.worldedit.regions.CylinderRegion;
import com.sk89q.worldedit.regions.EllipsoidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.CuboidRegion;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.regions.shape.CuboidShape;
import fr.aumgn.dac2.arena.regions.shape.CylinderShape;
import fr.aumgn.dac2.arena.regions.shape.EllipsoidShape;
import fr.aumgn.dac2.arena.regions.shape.FlatShape;
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

    public static StartRegion createStartRegion(DAC dac, Region weRegion) {
        return new StartRegion(create(dac, weRegion));
    }

    private static Shape create(DAC dac, Region weRegion) {
        if (weRegion instanceof CuboidRegion) {
            return new CuboidShape((CuboidRegion) weRegion);
        } else if (weRegion instanceof CylinderRegion) {
            return new CylinderShape((CylinderRegion) weRegion);
        } else if (weRegion instanceof EllipsoidRegion) {
            return new EllipsoidShape((EllipsoidRegion) weRegion);
        } else {
            throw new WERegionNotSupported(dac, weRegion.getClass());
        }
    }

    private WERegionFactory() {
    }
}
