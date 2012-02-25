package fr.aumgn.dac.api.event.game;

import fr.aumgn.dac.api.event.DACEvent;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.event.GameEvent;

public abstract class DACGameEvent extends DACEvent {

    protected GameEvent gameEvent;

    public DACGameEvent(GameEvent gameEvent) {
        super();
        this.gameEvent = gameEvent;
    }

    protected GameEvent getGameEvent() {
        return gameEvent;
    }

    public Game getGame() {
        return gameEvent.getGame();
    }
    
    public void send(String message) {
        gameEvent.send(message);
    }
    
}
