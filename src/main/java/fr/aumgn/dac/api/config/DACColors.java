package fr.aumgn.dac.api.config;

import java.util.Map.Entry;
import java.util.Set;

/**
 * Class which manages available {@link DACColor}.
 */
public interface DACColors {

    DACColor get(String name);

    /**
     * Get the first available color.
     */
    DACColor first();

    int size();

    /**
     * Gets a random color
     */
    DACColor random();

    Set<Entry<String, DACColor>> colors();
}