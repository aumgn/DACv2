package fr.aumgn.dac2.game.suddendeath;

import fr.aumgn.dac2.game.GamePlayer;
import fr.aumgn.dac2.stage.StagePlayer;

public class SuddenDeathPlayer extends GamePlayer {

    public enum Status {

        Idle,
        Failed,
        Success;

        public String localizationKey() {
            return name().toLowerCase();
        }
    }

    private Status status;

    public SuddenDeathPlayer(StagePlayer player) {
        super(player);
        status = Status.Idle;
    }

    public boolean hasSucceeded() {
        return status == Status.Success;
    }

    public void resetStatus() {
        status = Status.Idle;
    }

    public void setSuccess() {
        status = Status.Success;
    }

    public void setFail() {
        status = Status.Failed;
    }
}
