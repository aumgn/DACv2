package fr.aumgn.dac2.event;

import fr.aumgn.dac2.game.Game;

public abstract class DACGameEvent extends DACStageEvent {

    public DACGameEvent(Game game) {
        super(game);
    }

    public Game getStage() {
        return (Game) super.getStage();
    }

    public Game getGame() {
        return getStage();
    }
}
