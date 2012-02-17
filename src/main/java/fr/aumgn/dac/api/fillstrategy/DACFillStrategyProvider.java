package fr.aumgn.dac.api.fillstrategy;

import java.util.List;

public interface DACFillStrategyProvider {

    List<Class<? extends FillStrategy>> getFillStrategies();

}
