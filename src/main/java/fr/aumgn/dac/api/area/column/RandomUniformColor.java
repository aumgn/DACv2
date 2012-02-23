package fr.aumgn.dac.api.area.column;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.AreaColumn;

public class RandomUniformColor extends UniformColumn {
	
	public RandomUniformColor() {
		super(DAC.getConfig().getColors().random());
	}

	@Override
	public void set(AreaColumn column) {
		super.set(column);
		color = DAC.getConfig().getColors().random();
	}

}
