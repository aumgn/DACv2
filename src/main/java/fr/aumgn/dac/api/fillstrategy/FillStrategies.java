package fr.aumgn.dac.api.fillstrategy;

/**
 * Class which manages available {@link FillStrategy}.   
 */
public interface FillStrategies {

    FillStrategy get(String name);

}
