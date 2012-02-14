package fr.aumgn.dac.area.filler;

import java.util.Random;

import org.bukkit.Material;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.area.Area;
import fr.aumgn.dac.area.column.AreaColumn;

public class AreaLilipadStrategy implements AreaFillStrategy {

	private int percentage;

	public AreaLilipadStrategy(int percentage) {
		this.percentage = Math.min(Math.max(1, percentage), 100);
	}

	@Override
	public void fill(Area area) {
		Random rand = DAC.getRand();
		for (AreaColumn column : area.columns()) {
			column.set(Material.STATIONARY_WATER);
			if (rand.nextInt(100) < percentage) {
				column.getWorld().getBlockAt(
						column.getX(),
						column.getTop() + 1,
						column.getZ()).setType(Material.WATER_LILY);
			} else {
				column.getWorld().getBlockAt(
						column.getX(),
						column.getTop() + 1,
						column.getZ()).setType(Material.AIR);
			}
		}
	}

}
