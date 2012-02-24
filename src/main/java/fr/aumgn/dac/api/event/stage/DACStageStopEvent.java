package fr.aumgn.dac.api.event.stage;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.stage.Stage;

public class DACStageStopEvent extends DACStageEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACStageStopEvent(Stage stage) {
        super(stage);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
