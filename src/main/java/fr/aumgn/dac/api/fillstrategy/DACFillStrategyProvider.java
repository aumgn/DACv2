package fr.aumgn.dac.api.fillstrategy;

import java.util.List;

/**
 * Flag interface for class which provides {@link FillStrategy}. 
 * If the subclass isn't a Bukkit registered plugin main 
 * class this will do nothing.
 */ 
public interface DACFillStrategyProvider {

    /**
     * Gets the strategies provided.
     * 
     * @return a list of the strategies
     */
    List<Class<? extends FillStrategy>> getFillStrategies();

}
