package fr.aumgn.dac.areas.filler;

import java.util.Random;

import org.bukkit.Material;

import fr.aumgn.dac.areas.DACArea;
import fr.aumgn.dac.areas.column.DACColumn;

public class DACAreaLilipadFiller implements DACAreaFiller {
	
	private int percentage;

	public DACAreaLilipadFiller(int percentage) {
		this.percentage = Math.min(Math.max(1, percentage), 100);
	}
	
	@Override
	public void fill(DACArea area) {
		Random rand = new Random();
		for (DACColumn column : area.columns()) {
			column.set(Material.STATIONARY_WATER);
			if (rand.nextInt(100) < percentage) {
				column.getWorld().getBlockAt(
					column.getX(),
					column.getTop() + 1,
					column.getZ()
				).setType(Material.WATER_LILY);
			} else {
				column.getWorld().getBlockAt(
						column.getX(),
						column.getTop() + 1,
						column.getZ()
				).setType(Material.AIR);
			}
		}
	}

}
