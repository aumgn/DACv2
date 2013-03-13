package fr.aumgn.dac2.game.training;

import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.dac2.game.GamePlayer;
import fr.aumgn.dac2.stage.StagePlayer;

public class TrainingPlayer extends GamePlayer {

    public static class Factory
            implements GamePlayer.Factory<TrainingPlayer> {

        @Override
        public Class<TrainingPlayer> getSubclass() {
            return TrainingPlayer.class;
        }

        @Override
        public TrainingPlayer create(StagePlayer player, int index) {
            return new TrainingPlayer(player, index);
        }
    }

    private int successes;
    private int dacs;
    private int fails;

    public TrainingPlayer(StagePlayer player, int index) {
        super(player, index);
        this.successes = 0;
        this.dacs = 0;
        this.fails = 0;
    }

    public void incrementSuccesses() {
        successes++;
    }

    public void incrementDacs() {
        dacs++;
    }

    public void incrementFails() {
        fails++;
    }

    public int getSuccesses() {
        return successes;
    }

    public int getDacs() {
        return dacs;       
    }

    public int getFails() {
        return fails;
    }

    public void sendStats(PluginMessages messages) {
        sendMessage(messages.get("training.stats.successes", successes));
        sendMessage(messages.get("training.stats.dacs", dacs));
        sendMessage(messages.get("training.stats.fails", fails));
    }
}
