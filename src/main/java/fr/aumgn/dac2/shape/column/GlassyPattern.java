package fr.aumgn.dac2.shape.column;

import org.bukkit.Material;
import org.bukkit.World;

public class GlassyPattern implements ColumnPattern {

    private ColumnPattern pattern;

    public GlassyPattern(ColumnPattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public void apply(World world, Column column) {
        pattern.apply(world, column);
        column.get(column.getMaxY()).toBlock(world).setType(Material.GLASS);
    }
}
