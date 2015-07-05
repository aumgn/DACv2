package fr.aumgn.dac2.arena.regions;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.shape.FlatShape;
import fr.aumgn.dac2.shape.Shape;
import fr.aumgn.dac2.shape.column.Column;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

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

    public int size2D() {
        int size = 0;
        for (Column column : shape) {
            size++;
        }

        return size;
    }

    public Column getColumn(Vector2D pt) {
        return new Column(shape, pt);
    }

    public Column getColumn(Player player) {
        Location location = player.getLocation();
        return new Column(shape, new Vector2D(location.getBlockX(), location.getBlockZ()));
    }

    public void reset(World world) {
        fill(world, Material.STATIONARY_WATER);
    }

    public void fill(World world, Material material) {
        fill(world, material, (byte) 0);
    }

    public void fill(World world, Material material, byte data) {
        for (Column column : shape) {
            column.set(world, material, data);
        }
    }

    public boolean isFilled(World world) {
        for (Column column : shape) {
            if (column.isWater(world)) {
                return false;
            }
        }

        return true;
    }
}
