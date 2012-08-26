package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;

public class IncompleteArena extends DACException {

    private static final long serialVersionUID = -8929105621174257428L;

    public IncompleteArena(DAC dac, Arena arena) {
        super(dac.getMessages().get("arena.incomplete"));
    }
}
