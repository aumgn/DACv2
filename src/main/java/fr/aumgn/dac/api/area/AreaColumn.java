package fr.aumgn.dac.api.area;

import org.bukkit.Material;

/**
 * Represents a column of blocks in a {@link VerticalArea}.
 */
public interface AreaColumn extends Iterable<AreaColumnBlock> {

    /**
     * Gets the {@VerticalArea} associated with this column.
     * 
     * @return the vertical area inside which this column is. 
     */
    VerticalArea getArea();
    
    /**
     * Gets the x position of this column.
     * 
     * @return x position
     */
    int getX();

    /**
     * Gets the z position of this column.
     * 
     * @return z position
     */
    int getZ();

    /**
     * Gets the lower y position of this column.
     * 
     * @return this column bottom y value
     */
    int getBottom();

    /**
     * Gets the upper y position of this column.
     * 
     * @return this column top y value
     */
    int getTop();

    /**
     * Gets the height of this column.
     * 
     * @return this column height
     */
    int getHeight();

    /**
     * Gets the {@link AreaColumnBlock} at the given index.
     * <p/>
     * The index 0 stands for the lower block.
     * Out of range index will be used modulo the height.
     * A negative value can be used to get the nth element starting from the top.
     * (ie. get(-1) will returns the higher block).
     * 
     * @param index the index of the block
     * @return the AreaColumnBlock for the given index
     */
    AreaColumnBlock get(int index);

    /**
     * Changes blocks of column using the given {@link ColumnPattern}.
     * 
     * @param pattern the pattern to use
     */
    void set(ColumnPattern pattern);

    /**
     * Replaces all blocks with the given Material and data.
     * 
     * @param material the material to replace all blocks with
     * @param data the date 
     */
    void set(Material material, byte data);

    /**
     * @see #set(Material, byte)
     */
    void set(Material material);

    /**
     * Checks if a column is a water column.
     * <p/>
     * Actually only checks the higher block because that's what matters
     * with the `Dé a coudre` game.
     * 
     * @return whether the column is a water column or not
     */
    boolean isWater();

}
