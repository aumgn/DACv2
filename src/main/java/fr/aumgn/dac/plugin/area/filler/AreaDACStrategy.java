package fr.aumgn.dac.plugin.area.filler;

import org.bukkit.Material;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.AreaFillStrategy;
import fr.aumgn.dac.plugin.area.DACArea;

public class AreaDACStrategy implements AreaFillStrategy {

	private boolean sameParity;

	private boolean isDACColumn(AreaColumn column) {
		return ((column.getX() & 1) == (column.getZ() & 1)) == sameParity;
	}

	@Override
	public void fill(DACArea area) {
		sameParity = DAC.getRand().nextBoolean();
		for (AreaColumn column : area.columns()) {
			if (isDACColumn(column)) {
				column.set(Material.STATIONARY_WATER);
			} else {
				column.set(Material.WOOL);
			}
		}
	}

}
