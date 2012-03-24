package fr.aumgn.dac.api.fillstrategy;

import java.util.Random;

import org.bukkit.Material;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.area.VerticalArea;
import fr.aumgn.dac.api.area.column.RandomUniformColumn;

/**
 * Replaces all column (except a random one) of a {@link VerticalArea} with wool column. 
 */
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
        } while (!region.contains(new Vector(x, y, z)));

        ColumnPattern pattern = new RandomUniformColumn();
        for (AreaColumn column : area.columns()) {
            if (column.getX() != x || column.getZ() != z) {
                column.set(pattern);
            } else {
                column.set(Material.STATIONARY_WATER);
            }
        }
    }

}
