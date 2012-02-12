package fr.aumgn.dac.areas.filler;

import java.util.Random;

import org.bukkit.Material;

import fr.aumgn.dac.areas.DACArea;
import fr.aumgn.dac.areas.column.DACColumn;

public class DACAreaDACFiller implements DACAreaFiller {
	
	private boolean even;

	private boolean isDACColumn(DACColumn column) {
		return ((column.getX() % 2) == (column.getZ() % 2)) == even;
	}

	@Override
	public void fill(DACArea area) {
		even = new Random().nextBoolean();
		for (DACColumn column : area.columns()) {
			if (isDACColumn(column)) {
				column.set(Material.STATIONARY_WATER);
			} else {
				column.set(Material.WOOL);
			}
		}
	}

}
