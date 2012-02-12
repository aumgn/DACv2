package fr.aumgn.dac.area.filler;

import java.util.Random;

import org.bukkit.Material;

import fr.aumgn.dac.area.Area;
import fr.aumgn.dac.area.column.AreaColumn;

public class AreaDACFiller implements AreaFiller {
	
	private boolean sameParity;

	private boolean isDACColumn(AreaColumn column) {
		return ((column.getX() & 1) == (column.getZ() & 1)) == sameParity;
	}

	@Override
	public void fill(Area area) {
		sameParity = new Random().nextBoolean();
		for (AreaColumn column : area.columns()) {
			if (isDACColumn(column)) {
				column.set(Material.STATIONARY_WATER);
			} else {
				column.set(Material.WOOL);
			}
		}
	}

}
