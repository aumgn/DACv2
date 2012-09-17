package fr.aumgn.dac.plugin.mode.training;

import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.SimpleGamePlayer;
import fr.aumgn.dac.api.stage.StagePlayer;

public class TrainingGamePlayer extends SimpleGamePlayer {

    private int successes = 0;
    private int dacs = 0;
    private int fails = 0;

    public TrainingGamePlayer(Game stage, StagePlayer player) {
        super(stage, player);
    }

    public void incrementSuccesses() {
        this.successes++;
    }

    public void incrementDACs() {
        this.dacs++;
    }

    public void incrementFails() {
        this.fails++;
    }

    public void sendStats() {
        send(DACMessage.StatsSuccess.getContent(successes));
        send(DACMessage.StatsDAC.getContent(dacs));
        send(DACMessage.StatsFail.getContent(fails));
    }

}
