package fr.aumgn.dac.api.event.stage;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.stage.Stage;

public class DACStageStartEvent extends DACStageEvent {

    private static final long serialVersionUID = 1L;
    private static final HandlerList handlers = new HandlerList();

    public DACStageStartEvent(Stage<?> stage) {
        super("DACStageStartEvent", stage);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
