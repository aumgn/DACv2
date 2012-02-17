/*
 * Adaptation of WorldEdit RegionIterator (by TomyLobo)
 */

package fr.aumgn.dac.plugin.area;

import java.util.Iterator;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.VerticalArea;

public class AreaVerticalIterator implements Iterator<AreaColumn> {

    private VerticalArea area;
    private Region region;
    private int x;
    private int z;
    private int minX;
    private int maxX;
    private int maxZ;
    private int y;

    public AreaVerticalIterator(VerticalArea area) {
        this.area = area;
        this.region = area.getWERegion();
        Vector minPoint = region.getMinimumPoint();
        Vector maxPoint = region.getMaximumPoint();
        this.minX = minPoint.getBlockX();
        this.x = minX;
        this.z = minPoint.getBlockZ();
        this.maxX = maxPoint.getBlockX();
        this.maxZ = maxPoint.getBlockZ();
        this.y = minPoint.getBlockY();

        forward();
    }

    @Override
    public boolean hasNext() {
        return x != Integer.MIN_VALUE;
    }

    private void forward() {
        while (hasNext() && !region.contains(new BlockVector(x, y, z))) {
            forwardOne();
        }
    }

    @Override
    public AreaColumn next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        }

        AreaColumn answer = new DACAreaColumn(area, x, z);

        forwardOne();
        forward();

        return answer;
    }

    private void forwardOne() {
        if (++x <= maxX) {
            return;
        }
        x = minX;

        if (++z <= maxZ) {
            return;
        }
        x = Integer.MIN_VALUE;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
