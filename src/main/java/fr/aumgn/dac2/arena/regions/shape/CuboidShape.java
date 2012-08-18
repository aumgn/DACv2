package fr.aumgn.dac2.arena.regions.shape;

import static fr.aumgn.dac2.utils.WEUtils.*;

import org.bukkit.World;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.regions.CuboidRegion;

import fr.aumgn.bukkitutils.geom.Vector;

@ShapeName("cuboid")
public class CuboidShape implements FlatShape {

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
        return pt.isInside(min, max);
    }

    @Override
    public CuboidSelection getSelection(World world) {
        return new CuboidSelection(world, bu2we(min), bu2we(max));
    }
}
