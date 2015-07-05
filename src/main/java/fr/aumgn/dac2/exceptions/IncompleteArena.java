package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;

/**
 * Thrown when trying to start a stage in an incomplete arena.
 */
public class IncompleteArena extends DACException {

    public IncompleteArena(DAC dac, Arena arena) {
        super(dac.getMessages().get("arena.incomplete"));
    }
}
