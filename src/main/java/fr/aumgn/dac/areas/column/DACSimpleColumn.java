package fr.aumgn.dac.areas.column;

import org.bukkit.block.Block;

import fr.aumgn.dac.config.DACColor;

public class DACSimpleColumn implements DACColumnPattern {

	@Override
	public void place(DACColumn column, DACColor color) {
		for (Block block : column) {
			block.setType(color.getMaterial());
			block.setData(color.getData());
		}
	}

}
