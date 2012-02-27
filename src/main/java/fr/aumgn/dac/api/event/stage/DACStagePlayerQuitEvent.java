package fr.aumgn.dac.api.event.stage;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;

public class DACStagePlayerQuitEvent extends DACStageEvent {

    private static final HandlerList handlers = new HandlerList();

    private StagePlayer player;

    public DACStagePlayerQuitEvent(Stage stage, StagePlayer player) {
        super(stage);
        this.player = player;

    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public StagePlayer getPlayer() {
        return player;
    }

}
