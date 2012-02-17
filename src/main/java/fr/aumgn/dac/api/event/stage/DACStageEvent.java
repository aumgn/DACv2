package fr.aumgn.dac.api.event.stage;

import fr.aumgn.dac.api.event.DACEvent;
import fr.aumgn.dac.api.stage.Stage;

@SuppressWarnings("serial")
public class DACStageEvent extends DACEvent {

    private Stage<?> stage;

    public DACStageEvent(String name, Stage<?> stage) {
        super(name);
        this.stage = stage;
    }

    public Stage<?> getStage() {
        return stage;
    }

}
