package fr.aumgn.dac2.game.training;

import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.dac2.game.GamePlayer;
import fr.aumgn.dac2.stage.JoinPlayerData;

public class TrainingPlayer extends GamePlayer {

    private int successes;
    private int dacs;
    private int fails;

    public TrainingPlayer(PlayerId playerId, JoinPlayerData joinData) {
        super(playerId, joinData);
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

    public void sendStats(PluginMessages messages) {
        messages.get("training.stats.successes", successes);
        messages.get("training.stats.dacs", dacs);
        messages.get("training.stats.fails", fails);
    }
}
