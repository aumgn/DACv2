package fr.aumgn.dac2.shape;

import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.World;

import com.google.common.collect.AbstractLinkedIterator;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;

public class Column implements Iterable<Vector> {

    private final double minY;
    private final double maxY;
    private final Vector2D pt2D;

    public Column(FlatShape shape, Vector2D pt2D) {
        this.minY = shape.getMinY();
        this.maxY = shape.getMaxY();
        this.pt2D = pt2D;
    }

    public Vector get(double y) {
        Validate.isTrue(y >= minY && y <= maxY);
        return pt2D.to3D(y);
    }

    @Override
    public Iterator<Vector> iterator() {
        return new AbstractLinkedIterator<Vector>(pt2D.to3D(minY)) {
            @Override
            protected Vector computeNext(Vector pt) {
                return (pt.getY() >= maxY) ? null : pt.addY(1);
            }
        };
    }

    public void set(World world, Material block, short data) {
        for (Vector pt : this) {
            pt.toBlock(world).setType(block);
            pt.toBlock(world).setData((byte) data);
        }
    }

    public boolean isWater(World world) {
        Material type = pt2D.to3D(maxY).toBlock(world).getType();
        return type == Material.WATER || type == Material.STATIONARY_WATER;
    }
}
