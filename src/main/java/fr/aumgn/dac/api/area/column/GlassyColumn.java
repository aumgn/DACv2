package fr.aumgn.dac.api.area.column;

import org.bukkit.Material;

import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.config.DACColor;

/**
 * Represents A column which have a glass block on top.
 */
public class GlassyColumn implements ColumnPattern {
    
    private DACColor color; 
    
    public GlassyColumn(DACColor color) {
        this.color = color; 
    }

    @Override
    public void set(AreaColumn column) {
        column.set(color.getMaterial(), color.getData());
        column.get(-1).setType(Material.GLASS);
    }

}
