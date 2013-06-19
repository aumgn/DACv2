package fr.aumgn.dac2.event.player;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac2.event.DACStagePlayerEvent;
import fr.aumgn.dac2.stage.Stage;
import fr.aumgn.dac2.stage.StagePlayer;

public class DACPlayerQuitEvent extends DACStagePlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACPlayerQuitEvent(Stage stage, StagePlayer player) {
        super(stage, player);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
