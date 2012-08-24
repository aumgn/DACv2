package fr.aumgn.dac2.arena.regions;

import org.bukkit.Material;
import org.bukkit.World;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.shape.Column;
import fr.aumgn.dac2.shape.FlatShape;
import fr.aumgn.dac2.shape.Shape;

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
    public FlatShape getShape() {
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

    public boolean isADAC(World world, Vector2D pt) {
        boolean water;
        Vector pt3D = pt.to3D(shape.getMaxY());

        water =  isWater(world, pt3D.subtractX(1));
        water |= isWater(world, pt3D.addX(1));
        water |= isWater(world, pt3D.subtractZ(1));
        water |= isWater(world, pt3D.addZ(1));

        return !water;
    }

    private boolean isWater(World world, Vector pt) {
        Material type = pt.toBlock(world).getType();
        return type == Material.WATER || type == Material.STATIONARY_WATER;
    }

    public void putColumn(World world, Vector2D pt, Color color) {
        shape.getColumn(pt).set(world, color.block, color.data);
    }

    public void putDACColumn(World world, Vector2D pt, Color color) {
        putColumn(world, pt, color);
        pt.to3D(shape.getMaxY()).toBlock(world).setType(Material.GLASS);
    }
}
