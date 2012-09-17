package fr.aumgn.dac.api.area;

import org.bukkit.Material;

/**
 * Represents a column of blocks in a {@link VerticalArea}.
 */
public interface AreaColumn extends Iterable<AreaColumnBlock> {

    /**
     * Gets the {@link VerticalArea} associated with this column.
     */
    VerticalArea getArea();

    int getX();

    int getZ();

    int getBottom();

    int getTop();

    int getHeight();

    /**
     * Gets the {@link AreaColumnBlock} at the given index.
     * <p/>
     * The index 0 stands for the lower block.
     * Out of range index will be used modulo the height.
     * A negative value can be used to get the nth element starting from the top.
     * (ie. get(-1) will returns the higher block).
     */
    AreaColumnBlock get(int index);

    void set(ColumnPattern pattern);

    void set(Material material, byte data);

    void set(Material material);

    /**
     * Checks if a column is a water column.
     * <p/>
     * Actually only checks the higher block because that's what matters
     * with the `Dé a coudre` game.
     */
    boolean isWater();

}
