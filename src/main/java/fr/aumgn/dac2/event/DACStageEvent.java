package fr.aumgn.dac2.event;

import fr.aumgn.dac2.stage.Stage;

public abstract class DACStageEvent extends DACEvent {

    private final Stage stage;

    public DACStageEvent(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
