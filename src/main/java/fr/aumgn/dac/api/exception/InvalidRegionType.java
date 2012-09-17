package fr.aumgn.dac.api.exception;

import com.sk89q.worldedit.regions.Region;

/**
 * Thrown when trying to use a region type which is not supported. 
 */
public class InvalidRegionType extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidRegionType(String expected1, String expected2, String given) {
        super("Expected " + expected1 + " or " + expected2 + " got " + given);
    }

    public InvalidRegionType(String expected1, String expected2, Class<? extends Region> given) {
        this(expected1, expected2, given.getSimpleName());
    }

}
