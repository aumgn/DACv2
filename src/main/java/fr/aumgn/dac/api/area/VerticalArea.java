package fr.aumgn.dac.api.area;

import com.sk89q.worldedit.BlockVector2D;

import fr.aumgn.dac.api.fillstrategy.FillStrategy;

/**
 * Represents an {@link Area} which has the same height in all direction.
 */
public interface VerticalArea extends Area {

    /**
     * Gets the lower y.
     * 
     * @return the lower y
     */
    public int getBottom();

    /**
     * Gets the upper y.
     * 
     * @return the upper y
     */
    public int getTop();

    /**
     * Gets the column at the given vector position.
     * <p/>
     * For now, this method do not check if the column 
     * is actually in the pool.
     *   
     * @param vec the vector 
     * @return the column for the given x and y position.
     */
    AreaColumn getColumn(BlockVector2D vec);

    /**
     * Gets the column at the given x and y position.
     * <p/>
     * For now, this method do not check if the column 
     * is actually in the pool.
     *   
     * @param x the x position
     * @param z the y position
     * @return the column for the given x and y position.
     */
    AreaColumn getColumn(int x, int z);

    /**
     * Checks if the pool does not contain any water columns. 
     * 
     * @return whether or not the pool is full 
     */
    boolean isFull();

    /**
     * Fills the area with the given {@link FillStrategy} strategy.
     * 
     * @param strategy the strategy to use to fill this area.
     * @param fillArgs arguments to use with the FillStrategy.
     */
    void fillWith(FillStrategy strategy, String[] fillArgs);

    /**
     * Gets an iterator.
     *
     * @return iterator of column inside this vertical area.
     */
    Iterable<AreaColumn> columns();

}
