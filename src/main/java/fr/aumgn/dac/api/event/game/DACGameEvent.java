package fr.aumgn.dac.api.event.game;

import fr.aumgn.dac.api.event.DACEvent;
import fr.aumgn.dac.api.game.Game;

public abstract class DACGameEvent extends DACEvent {

    private Game game;

    public DACGameEvent(Game game) {
        super();
        this.game = game;
    }

   public Game getGame() {
        return game;
    }

}
