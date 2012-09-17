package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.game.Game;

public class GameNewTurn extends GameEvent {

    public GameNewTurn(Game game) {
        super(game);
    }

}
