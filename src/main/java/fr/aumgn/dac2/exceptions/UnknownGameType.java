package fr.aumgn.dac2.exceptions;

import fr.aumgn.dac2.DAC;

public class UnknownGameType extends DACException {

    private static final long serialVersionUID = 2000243296652979093L;

    public UnknownGameType(DAC dac, String type) {
        super(dac.getMessages().get("game.type.unknown", type));
    }
}
