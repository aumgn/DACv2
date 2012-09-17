package fr.aumgn.dac.api.area.column;

import org.bukkit.Material;

import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.config.DACColor;

/**
 * Represents a column pattern which has a glass block on top.
 */
public class GlassyColumn extends UniformColumn {

    public GlassyColumn(DACColor color) {
        super(color); 
    }

    @Override
    public void set(AreaColumn column) {
        super.set(column);
        column.get(-1).setType(Material.GLASS);
    }

}
