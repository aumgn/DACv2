package fr.aumgn.dac2.arena.regions.shape;

import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.CuboidRegion;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.exceptions.WERegionNotSupported;

public class WEShapeFactory {

    public static Shape create(DAC dac, Region weRegion) {
        if (!(weRegion instanceof CuboidRegion)) {
            throw new WERegionNotSupported(dac, weRegion.getClass());
        }

        return new CuboidShape((CuboidRegion) weRegion);
    }

    private WEShapeFactory() {
    }
}
