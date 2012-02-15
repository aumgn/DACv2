package fr.aumgn.dac.api.area.fillstrategy;

import java.util.Collection;

public interface FillStrategies {

	Collection<String> getNames();
	
	FillStrategy get(String name);

}
