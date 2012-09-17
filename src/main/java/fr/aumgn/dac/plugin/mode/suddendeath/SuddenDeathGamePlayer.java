package fr.aumgn.dac.plugin.mode.suddendeath;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.SimpleGamePlayer;
import fr.aumgn.dac.api.stage.StagePlayer;

public class SuddenDeathGamePlayer extends SimpleGamePlayer {

    private boolean deadThisTurn;

    public SuddenDeathGamePlayer(Game game, StagePlayer player) {
        super(game, player);
        this.deadThisTurn = false;
    }

    @Override
    public String formatForList() {
        if (deadThisTurn) {
            return super.formatForList() + " : En sursis"; 
        }
        return super.formatForList();
    }

    public boolean isDeadThisTurn() {
        return deadThisTurn;
    }

    public void setDeadThisTurn() {
        deadThisTurn = true;
    }

    public void cancelDeadThisTurn() {
        deadThisTurn = false;
    }

}
