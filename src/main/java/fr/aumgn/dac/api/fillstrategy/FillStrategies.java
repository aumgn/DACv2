package fr.aumgn.dac.api.fillstrategy;

import java.util.Collection;

/**
 * Class which manages available {@link FillStrategy}.   
 */
public interface FillStrategies {

    /**
     * Gets the names of available fill strategies. (Not aliases aware.)
     * 
     * @return a collection of available strategies names 
     */
    Collection<String> getNames();

    /**
     * Gets the {@link FillStrategy} for the given name.
     * 
     * @param name the name of the {@link FillStrategy}
     * @return the FillStrategy for the given name. 
     */
    FillStrategy get(String name);

}
