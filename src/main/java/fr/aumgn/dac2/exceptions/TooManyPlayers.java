package fr.aumgn.dac2.exceptions;

public class TooManyPlayers extends DACException {

    private static final long serialVersionUID = -2374736105366736407L;

    public TooManyPlayers(String message) {
        super(message);
    }
}
