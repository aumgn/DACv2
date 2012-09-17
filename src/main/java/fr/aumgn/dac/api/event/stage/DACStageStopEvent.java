package fr.aumgn.dac.api.event.stage;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StageStopReason;

public class DACStageStopEvent extends DACStageEvent {

    private static final HandlerList handlers = new HandlerList();

    private StageStopReason reason;
    
    public DACStageStopEvent(Stage stage, StageStopReason reason) {
        super(stage);
        this.reason = reason;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public StageStopReason getReason() {
        return reason;
    }

}
