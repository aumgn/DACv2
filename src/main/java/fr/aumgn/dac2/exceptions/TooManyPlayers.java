package fr.aumgn.dac2.exceptions;

/**
 * Thrown when trying to start a game with too many players.
 */
public class TooManyPlayers extends DACException {

    public TooManyPlayers(String message) {
        super(message);
    }
}
