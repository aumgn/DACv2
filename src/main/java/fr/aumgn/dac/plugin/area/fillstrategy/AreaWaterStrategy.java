package fr.aumgn.dac.plugin.area.fillstrategy;

import org.bukkit.Material;

import fr.aumgn.dac.api.area.fillstrategy.DACFillStrategy;

@DACFillStrategy(name="water")
public class AreaWaterStrategy extends AreaSimpleStrategy {

	public AreaWaterStrategy() {
		super(Material.STATIONARY_WATER);
	}

}
