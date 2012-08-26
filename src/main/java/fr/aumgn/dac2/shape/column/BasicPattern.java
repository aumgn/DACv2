package fr.aumgn.dac2.shape.column;

import org.bukkit.Material;
import org.bukkit.World;

public class BasicPattern implements ColumnPattern {

    private final Material material;
    private final byte data;

    public BasicPattern(Material material) {
        this(material, 0);
    }

    public BasicPattern(Material material, int data) {
        this.material = material;
        this.data = (byte) data;
    }

    @Override
    public void apply(World world, Column column) {
        column.set(world, material, data);
    }
}
