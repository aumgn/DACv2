package fr.aumgn.dac.api.fillstrategy.defaults;

import java.util.Random;

import org.bukkit.Material;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.VerticalArea;
import fr.aumgn.dac.api.fillstrategy.DACFillStrategy;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;

/**
 * Replaces all column (except a random one) of a {@link VerticalArea} with wool column. 
 */
@DACFillStrategy(name = "all-but-one", aliases = { "abo" })
public class FillAllButOne implements FillStrategy {

    @Override
    public void fill(VerticalArea area, String[] args) {
        Random rand = DAC.getRand();
        Region region = area.getWERegion();
        Vector minPoint = region.getMinimumPoint();
        Vector maxPoint = region.getMaximumPoint();
        int x, z, y = minPoint.getBlockY();
        int minX = minPoint.getBlockX();
        int minZ = minPoint.getBlockZ();
        int xRange = maxPoint.getBlockX() - minX;
        int zRange = maxPoint.getBlockZ() - minZ;
        do {
            x = minX + rand.nextInt(xRange);
            z = minZ + rand.nextInt(zRange);
        } while (!region.contains(new BlockVector(x, y, z)));

        for (AreaColumn column : area.columns()) {
            if (column.getX() != x || column.getZ() != z) {
                column.set(Material.WOOL, (byte) 0);
            } else {
                column.set(Material.STATIONARY_WATER);
            }
        }
    }

}
