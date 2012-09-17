package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StageStopReason;

public class GameFinish extends GameEvent {

    private StageStopReason reason;
    private boolean poolReset;

    public GameFinish(Game game, StageStopReason reason) {
        super(game);
        this.reason = reason;
        this.poolReset = DAC.getConfig().getResetOnEnd();
    }

    public StageStopReason getReason() {
        return reason;
    }

    public boolean getPoolReset() {
        return poolReset;
    }

    public void setPoolReset(boolean poolReset) {
        this.poolReset = poolReset;
    }

}
