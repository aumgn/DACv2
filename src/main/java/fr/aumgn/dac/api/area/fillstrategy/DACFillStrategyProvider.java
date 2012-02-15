package fr.aumgn.dac.api.area.fillstrategy;

import java.util.List;

public interface DACFillStrategyProvider {

	List<Class<? extends FillStrategy>> getFillStrategies();
	
}
