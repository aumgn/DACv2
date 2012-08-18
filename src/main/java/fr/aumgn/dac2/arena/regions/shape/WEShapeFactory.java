package fr.aumgn.dac2.arena.regions.shape;

import com.sk89q.worldedit.regions.CylinderRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.CuboidRegion;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.exceptions.WERegionNotSupported;

public class WEShapeFactory {

    public static Shape create(DAC dac, Region weRegion) {
        if (weRegion instanceof CuboidRegion) {
            return new CuboidShape((CuboidRegion) weRegion);
        } else if (weRegion instanceof CylinderRegion) {
            return new CylinderShape((CylinderRegion) weRegion);
        } else {
            throw new WERegionNotSupported(dac, weRegion.getClass());
        }
    }

    private WEShapeFactory() {
    }
}
