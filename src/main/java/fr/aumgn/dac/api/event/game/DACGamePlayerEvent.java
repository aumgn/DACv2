package fr.aumgn.dac.api.event.game;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StagePlayer;

public abstract class DACGamePlayerEvent extends DACGameEvent {

    private StagePlayer player;

    public DACGamePlayerEvent(Game game, StagePlayer player) {
        super(game);
        this.player = player;
    }

    public StagePlayer getPlayer() {
        return player;
    }

}
