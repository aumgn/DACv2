package fr.aumgn.dac.api.config;

/**
 * Class which manages available {@link DACColor}.
 */
public interface DACColors extends Iterable<DACColor> {

    /**
     * Gets the color with the given name.
     * 
     * @param name the name of the color to return
     * @return
     */
    DACColor get(String name);

    /**
     * Get the first color.
     * 
     * @return the first color
     */
    DACColor first();

    /**
     * Gets the number of colors available.
     * 
     * @return the number of colors available
     */
    int size();

}