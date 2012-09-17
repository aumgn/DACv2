package fr.aumgn.dac.api.game.event;

import java.util.List;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StagePlayer;

public class GameStart extends GameEvent {

    private boolean poolReset;

    public GameStart(Game game) {
        super(game);
        poolReset = DAC.getConfig().getResetOnStart();
    }

    public List<? extends StagePlayer> getPlayers() {
        return getGame().getPlayers();
    }

    public boolean getPoolReset() {
        return poolReset;
    }

    public void setPoolReset(boolean poolReset) {
        this.poolReset = poolReset;
    }

}
