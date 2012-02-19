package fr.aumgn.dac.api.area;

/**
 * Represents a strategy to replace blocks of a column. 
 */
public interface ColumnPattern {

    /**
     * Replaces blocks of the column.
     * 
     * @param column the column.
     */
    void set(AreaColumn column);

}
