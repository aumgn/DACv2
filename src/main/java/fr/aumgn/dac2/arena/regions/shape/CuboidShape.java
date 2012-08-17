package fr.aumgn.dac2.arena.regions.shape;

import static fr.aumgn.dac2.utils.WEUtils.*;

import com.sk89q.worldedit.regions.CuboidRegion;

import fr.aumgn.bukkitutils.geom.Vector;

@ShapeName("cuboid")
public class CuboidShape implements Shape {

    private final Vector min;
    private final Vector max;

    public CuboidShape(CuboidRegion region) {
        this.min = we2bu(region.getMinimumPoint());
        this.max = we2bu(region.getMaximumPoint());
    }

    public CuboidShape(Vector min, Vector max) {
        this.min = min;
        this.max = max;
    }

    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return max;
    }

    @Override
    public boolean contains(Vector pt) {
        return pt.getX() >= min.getX()
                && pt.getY() >= min.getY()
                && pt.getZ() >= min.getZ()
                && pt.getX() <= max.getX()
                && pt.getY() <= max.getY()
                && pt.getZ() <= max.getZ();
    }
}
