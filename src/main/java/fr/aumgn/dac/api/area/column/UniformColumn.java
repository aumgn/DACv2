package fr.aumgn.dac.api.area.column;

import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.config.DACColor;

/**
 * Represents an uniform column
 */
public class UniformColumn implements ColumnPattern {

    private DACColor color; 
    
    public UniformColumn(DACColor color) {
        this.color = color; 
    }

    @Override
    public void set(AreaColumn column) {
        column.set(color.getMaterial(), color.getData());
    }

}
