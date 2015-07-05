package fr.aumgn.dac2.event.stage;

import fr.aumgn.dac2.event.DACStageEvent;
import fr.aumgn.dac2.stage.Stage;
import org.bukkit.event.HandlerList;

public class DACStageStopEvent extends DACStageEvent {

    private static final HandlerList handlers = new HandlerList();

    private final boolean force;

    public DACStageStopEvent(Stage stage, boolean force) {
        super(stage);
        this.force = force;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isForce() {
        return force;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
