package fr.aumgn.dac2.exceptions;

/**
 * Thrown when trying to access with the safeGet* arena getters
 * an undefined component.
 * (either the pool, the start region, the diving or the surrounding region).
 */
public class ArenaComponentUndefined extends DACException {

    private static final long serialVersionUID = 1529213051137532423L;

    public ArenaComponentUndefined(String message) {
        super(message);
    }
}
