package fr.aumgn.dac2.game.suddendeath;

import java.util.Locale;

import fr.aumgn.dac2.game.GamePlayer;
import fr.aumgn.dac2.stage.StagePlayer;

public class SuddenDeathPlayer extends GamePlayer {

    public static class Factory
            implements GamePlayer.Factory<SuddenDeathPlayer> {

        @Override
        public Class<SuddenDeathPlayer> getSubclass() {
            return SuddenDeathPlayer.class;
        }

        @Override
        public SuddenDeathPlayer create(StagePlayer player, int index) {
            return new SuddenDeathPlayer(player, index);
        }
    }

    public enum Status {

        Awaiting,
        Failed,
        Success;
    }

    private Status status;

    public SuddenDeathPlayer(StagePlayer player, int index) {
        super(player, index);
        status = Status.Awaiting;
    }

    public boolean hasSucceeded() {
        return status == Status.Success;
    }

    public void resetStatus() {
        status = Status.Awaiting;
    }

    public void setSuccess() {
        status = Status.Success;
    }

    public void setFail() {
        status = Status.Failed;
    }

    public String getLocalizationKeyForStatus() {
        return status.name().toLowerCase(Locale.US);
    }
}