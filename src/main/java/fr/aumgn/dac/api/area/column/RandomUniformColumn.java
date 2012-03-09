package fr.aumgn.dac.api.area.column;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.AreaColumn;

/*
 * Represents a random column pattern.
 */
public class RandomUniformColumn extends UniformColumn {

    public RandomUniformColumn() {
        super(DAC.getConfig().getColors().random());
    }

    @Override
    public void set(AreaColumn column) {
        super.set(column);
        color = DAC.getConfig().getColors().random();
    }

}
