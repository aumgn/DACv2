package fr.aumgn.dac.api.config;

/**
 * Class which manages available {@link DACColor}.
 */
public interface DACColors extends Iterable<DACColor> {

    DACColor get(String name);

    /**
     * Get the first available color.
     */
    DACColor first();

    int amount();

    /**
     * Gets a random color
     */
    DACColor random();

}