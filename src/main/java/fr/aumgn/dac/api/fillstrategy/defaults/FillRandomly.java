package fr.aumgn.dac.api.fillstrategy.defaults;

import java.util.Random;

import org.bukkit.Material;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.area.VerticalArea;
import fr.aumgn.dac.api.area.column.RandomUniformColor;
import fr.aumgn.dac.api.fillstrategy.DACFillStrategy;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;

/**
 * Replaces random column of a {@link VerticalArea} with wool.
 * Uses a weight percentage options.  
 */
@DACFillStrategy(name = "randomly")
public class FillRandomly implements FillStrategy {

    public FillRandomly() {}

    @Override
    public void fill(VerticalArea area, String[] args) {
        int percentage;
        if (args.length == 0) {
            percentage = 50;
        } else {
            try {
                percentage = Integer.parseInt(args[0]);
                percentage = Math.min(Math.max(0, percentage), 100);
            } catch (NumberFormatException exc) {
                percentage = 50;
            }
        }
        Random rand = DAC.getRand();
        ColumnPattern pattern = new RandomUniformColor();
        for (AreaColumn column : area.columns()) {
            column.set(Material.STATIONARY_WATER);
            if (rand.nextInt(101) <= percentage) {
                column.set(pattern);
            } else {
                column.set(Material.STATIONARY_WATER);
            }
        }
    }

}
