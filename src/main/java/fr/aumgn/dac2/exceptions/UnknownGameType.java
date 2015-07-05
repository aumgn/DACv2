package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;

/**
 * Thrown when trying to get a game type which is not known by the plugin.
 */
public class UnknownGameType extends DACException {

    public UnknownGameType(DAC dac, String type) {
        super(dac.getMessages().get("game.type.unknown", type));
    }
}
