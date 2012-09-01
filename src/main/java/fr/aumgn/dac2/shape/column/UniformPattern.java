package fr.aumgn.dac2.shape.column;

import org.bukkit.Material;
import org.bukkit.World;

/**
 * A column pattern which set all blocks uniformly.
 */
public class UniformPattern implements ColumnPattern {

    private final Material material;
    private final byte data;

    public UniformPattern(Material material) {
        this(material, 0);
    }

    public UniformPattern(Material material, int data) {
        this.material = material;
        this.data = (byte) data;
    }

    @Override
    public void apply(World world, Column column) {
        column.set(world, material, data);
    }
}
