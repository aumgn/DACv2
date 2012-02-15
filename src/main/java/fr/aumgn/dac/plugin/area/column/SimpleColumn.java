package fr.aumgn.dac.plugin.area.column;

import org.bukkit.block.Block;

import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.config.DACColor;

public class SimpleColumn implements ColumnPattern {

	@Override
	public void set(AreaColumn column, DACColor color) {
		for (Block block : column) {
			block.setType(color.getMaterial());
			block.setData(color.getData());
		}
	}

}
