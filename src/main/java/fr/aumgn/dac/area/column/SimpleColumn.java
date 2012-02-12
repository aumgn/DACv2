package fr.aumgn.dac.area.column;

import org.bukkit.block.Block;

import fr.aumgn.dac.config.DACColor;

public class SimpleColumn implements ColumnPattern {

	@Override
	public void place(AreaColumn column, DACColor color) {
		for (Block block : column) {
			block.setType(color.getMaterial());
			block.setData(color.getData());
		}
	}

}
