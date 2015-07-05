package fr.aumgn.dac2.event.stage;

import fr.aumgn.dac2.event.DACStageEvent;
import fr.aumgn.dac2.stage.Stage;
import org.bukkit.event.HandlerList;

public class DACStageStartEvent extends DACStageEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACStageStartEvent(Stage stage) {
        super(stage);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
