package fr.aumgn.dac2.shape.column;

import org.bukkit.World;

/**
 * Represents a pattern used to change column's blocks.
 */
public interface ColumnPattern {

    void apply(World world, Column column);
}
