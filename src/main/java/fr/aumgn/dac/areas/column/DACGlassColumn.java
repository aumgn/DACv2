package fr.aumgn.dac.areas.column;

import org.bukkit.Material;
import org.bukkit.block.Block;

import fr.aumgn.dac.config.DACColor;

public class DACGlassColumn implements DACColumnPattern {

	@Override
	public void place(DACColumn column, DACColor color) {
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
