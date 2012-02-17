package fr.aumgn.dac.api.event.game;

import fr.aumgn.dac.api.event.DACEvent;
import fr.aumgn.dac.api.game.Game;

@SuppressWarnings("serial")
public class DACGameEvent extends DACEvent {

    private Game<?> game;

    public DACGameEvent(String name, Game<?> game) {
        super(name);
        this.game = game;
    }

    public Game<?> getGame() {
        return game;
    }

}
