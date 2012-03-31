package fr.aumgn.dac.plugin.area.region;

import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.regions.Region;

public abstract class DACCachedRegion implements DACRegion {

    private Region region;

    public abstract Region createWERegion(LocalWorld world);

    @Override
    public Region getRegion(LocalWorld world) {
        if (region == null || region.getWorld() != world) {
            region = createWERegion(world);
        }
        return region;
    }

}
