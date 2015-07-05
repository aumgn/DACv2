package fr.aumgn.dac2.event.player;

import fr.aumgn.dac2.event.DACStagePlayerEvent;
import fr.aumgn.dac2.stage.Stage;
import fr.aumgn.dac2.stage.StagePlayer;
import org.bukkit.event.HandlerList;

public class DACPlayerQuitEvent extends DACStagePlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACPlayerQuitEvent(Stage stage, StagePlayer player) {
        super(stage, player);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
