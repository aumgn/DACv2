package fr.aumgn.dac.plugin.mode.suddendeath;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.SimpleGamePlayer;
import fr.aumgn.dac.api.stage.StagePlayer;

public class SuddenDeathGamePlayer extends SimpleGamePlayer {

    private boolean deadThisTurn;
    private boolean dead;

    public SuddenDeathGamePlayer(Game<SuddenDeathGamePlayer> game, StagePlayer player, int index) {
        super(game, player, index);
        this.deadThisTurn = false;
        this.dead = false;
    }

    @Override
    public String formatForList() {
        return super.formatForList() + " : " + (dead ? "Mort" : "En vie");
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

    public boolean isDead() {
        return dead;
    }

    public void setDead() {
        deadThisTurn = false;
        dead = true;
    }

}
