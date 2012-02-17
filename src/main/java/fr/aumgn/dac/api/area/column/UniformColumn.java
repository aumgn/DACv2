package fr.aumgn.dac.api.area.column;

import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.config.DACColor;

public class UniformColumn implements ColumnPattern {

	@Override
	public void set(AreaColumn column, DACColor color) {
		column.set(color.getMaterial(), color.getData());
	}

}
