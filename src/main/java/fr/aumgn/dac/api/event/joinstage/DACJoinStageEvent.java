package fr.aumgn.dac.api.event.joinstage;

import fr.aumgn.dac.api.event.DACEvent;
import fr.aumgn.dac.api.joinstage.JoinStage;

public abstract class DACJoinStageEvent extends DACEvent {

    private JoinStage<?> joinStage;

    public DACJoinStageEvent(JoinStage<?> stage) {
        super();
        this.joinStage = stage;
    }

    public JoinStage<?> getJoinStage() {
        return joinStage;
    }

}
