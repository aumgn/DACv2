package fr.aumgn.dac2.exceptions;

/**
 * Thrown when trying to start a game with too many players.
 */
public class TooManyPlayers extends DACException {

    private static final long serialVersionUID = -2374736105366736407L;

    public TooManyPlayers(String message) {
        super(message);
    }
}
