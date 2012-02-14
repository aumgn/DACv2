package fr.aumgn.dac.area.filler;

import org.bukkit.Material;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.area.Area;
import fr.aumgn.dac.area.column.AreaColumn;

public class AreaDACStrategy implements AreaFillStrategy {

	private boolean sameParity;

	private boolean isDACColumn(AreaColumn column) {
		return ((column.getX() & 1) == (column.getZ() & 1)) == sameParity;
	}

	@Override
	public void fill(Area area) {
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
