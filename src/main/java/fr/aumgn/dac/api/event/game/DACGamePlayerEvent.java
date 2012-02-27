package fr.aumgn.dac.api.event.game;

import fr.aumgn.dac.api.game.event.GamePlayerEvent;
import fr.aumgn.dac.api.stage.StagePlayer;

public abstract class DACGamePlayerEvent extends DACGameEvent {

    public DACGamePlayerEvent(GamePlayerEvent gameEvent) {
        super(gameEvent);
    }

    @Override
    protected GamePlayerEvent getGameEvent() {
        return (GamePlayerEvent) gameEvent;
    }

    public StagePlayer getPlayer() {
        return getGameEvent().getPlayer();
    }

    public void sendToPlayer(String message) {
        getGameEvent().sendToPlayer(message);
    }

    public void sendToOthers(String message) {
        getGameEvent().sendToOthers(message);
    }

}
