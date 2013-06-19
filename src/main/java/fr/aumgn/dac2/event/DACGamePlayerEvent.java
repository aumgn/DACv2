package fr.aumgn.dac2.event;

import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GamePlayer;

public abstract class DACGamePlayerEvent extends DACGameEvent {

    private final GamePlayer player;

    public DACGamePlayerEvent(Game game, GamePlayer player) {
        super(game);
        this.player = player;
    }

    public GamePlayer getPlayer() {
        return player;
    }
}
