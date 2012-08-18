package fr.aumgn.dac2.arena.regions;

import org.bukkit.Material;
import org.bukkit.World;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.arena.regions.shape.Column;
import fr.aumgn.dac2.arena.regions.shape.FlatShape;
import fr.aumgn.dac2.arena.regions.shape.Shape;

public class Pool extends Region {

    private final FlatShape shape;

    // Constructor used with reflection
    @SuppressWarnings("unused")
    private Pool(Shape shape) {
        this((FlatShape) shape);
    }

    public Pool(FlatShape shape) {
        this.shape = shape;
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    public void reset(World world) {
        fill(world, Material.STATIONARY_WATER);
    }

    public void fill(World world, Material material) {
        for (Column column : shape) {
            for (Vector pt : column) {
                pt.toBlock(world).setType(material);
            }
        }
    }
}
