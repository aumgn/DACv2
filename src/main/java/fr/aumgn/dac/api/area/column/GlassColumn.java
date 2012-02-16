package fr.aumgn.dac.api.area.column;

import org.bukkit.Material;
import org.bukkit.block.Block;

import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.config.DACColor;

public class GlassColumn implements ColumnPattern {

	@Override
	public void set(AreaColumn column, DACColor color) {
		int yMax = column.getTop();
		for (Block block : column) {
			if (block.getY() == yMax) {
				block.setType(Material.GLASS);
			} else { 
				block.setType(color.getMaterial());
				block.setData(color.getData());
			}
		}
	}

}
