package fr.aumgn.dac.plugin.mode.training;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.SimpleGamePlayer;
import fr.aumgn.dac.api.stage.StagePlayer;

public class TrainingGamePlayer extends SimpleGamePlayer {

    private int successes = 0;
    private int dacs = 0;
    private int fails = 0;

    public TrainingGamePlayer(Game stage, StagePlayer player, int index) {
        super(stage, player, index);
    }

    public int getSuccesses() {
        return successes;
    }

    public void incrementSuccesses() {
        this.successes++;
    }

    public int getDACs() {
        return dacs;
    }

    public void incrementDACs() {
        this.dacs++;
    }

    public int getFails() {
        return fails;
    }

    public void incrementFails() {
        this.fails++;
    }

}
