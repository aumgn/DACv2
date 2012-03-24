package fr.aumgn.dac.api.fillstrategy;

import org.bukkit.Material;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.area.VerticalArea;
import fr.aumgn.dac.api.area.column.RandomUniformColumn;

/**
 * Replaces one column over two of a {@link VerticalArea} with wool. 
 */
public class FillDAC implements FillStrategy {

    private boolean sameParity;

    private boolean isDACColumn(AreaColumn column) {
        return ((column.getX() & 1) == (column.getZ() & 1)) == sameParity;
    }

    @Override
    public void fill(VerticalArea area, String[] args) {
        sameParity = DAC.getRand().nextBoolean();
        ColumnPattern pattern = new RandomUniformColumn();
        for (AreaColumn column : area.columns()) {
            if (isDACColumn(column)) {
                column.set(Material.STATIONARY_WATER);
            } else {
                column.set(pattern);
            }
        }
    }

}
