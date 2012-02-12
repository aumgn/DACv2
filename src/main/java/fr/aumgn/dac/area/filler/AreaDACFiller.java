package fr.aumgn.dac.area.filler;

import java.util.Random;

import org.bukkit.Material;

import fr.aumgn.dac.area.Area;
import fr.aumgn.dac.area.column.AreaColumn;

public class AreaDACFiller implements AreaFiller {
	
	private boolean even;

	private boolean isDACColumn(AreaColumn column) {
		return ((column.getX() % 2) == (column.getZ() % 2)) == even;
	}

	@Override
	public void fill(Area area) {
		even = new Random().nextBoolean();
		for (AreaColumn column : area.columns()) {
			if (isDACColumn(column)) {
				column.set(Material.STATIONARY_WATER);
			} else {
				column.set(Material.WOOL);
			}
		}
	}

}
