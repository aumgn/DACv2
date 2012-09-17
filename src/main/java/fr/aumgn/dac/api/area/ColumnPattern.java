package fr.aumgn.dac.api.area;

/**
 * Represents a strategy to replace blocks of a column. 
 */
public interface ColumnPattern {

    /**
     * Replaces blocks of the column with this pattern.
     */
    void set(AreaColumn column);

}
