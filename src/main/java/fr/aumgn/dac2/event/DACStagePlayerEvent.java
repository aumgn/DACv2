package fr.aumgn.dac2.event;

import fr.aumgn.dac2.stage.Stage;
import fr.aumgn.dac2.stage.StagePlayer;

public abstract class DACStagePlayerEvent extends DACStageEvent {

    private final StagePlayer player;

    public DACStagePlayerEvent(Stage stage, StagePlayer player) {
        super(stage);
        this.player = player;
    }

    public StagePlayer getPlayer() {
        return player;
    }
}
