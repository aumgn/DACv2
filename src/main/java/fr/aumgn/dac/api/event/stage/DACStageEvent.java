package fr.aumgn.dac.api.event.stage;

import fr.aumgn.dac.api.event.DACEvent;
import fr.aumgn.dac.api.stage.Stage;

public abstract class DACStageEvent extends DACEvent {

    private Stage<?> stage;

    public DACStageEvent(Stage<?> stage) {
        super();
        this.stage = stage;
    }

    public Stage<?> getStage() {
        return stage;
    }

}
