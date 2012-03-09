package fr.aumgn.dac.api.area;

import com.sk89q.worldedit.Vector2D;

import fr.aumgn.dac.api.fillstrategy.FillStrategy;

/**
 * Represents an {@link Area} which has the same height in all direction.
 */
public interface VerticalArea extends Area {

    public int getBottom();

    public int getTop();

    /**
     * Gets the column at the given vector position.
     * <p/>
     * For now, this method do not check if the column 
     * is actually in the pool.
     */
    AreaColumn getColumn(Vector2D vec);

    /**
     * Gets the column at the given x and y position.
     * <p/>
     * For now, this method do not check if the column 
     * is actually in the pool.
     */  
    AreaColumn getColumn(int x, int z);

    /**
     * Checks if the pool does not contain any water columns. 
     */
    boolean isFull();

    void fillWith(FillStrategy strategy, String[] fillArgs);

    Iterable<AreaColumn> columns();

}
