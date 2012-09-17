package fr.aumgn.dac.api.event.stage;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StageQuitReason;

public class DACStagePlayerQuitEvent extends DACStageEvent {

    private static final HandlerList handlers = new HandlerList();

    private StagePlayer player;
    private StageQuitReason reason;

    public DACStagePlayerQuitEvent(Stage stage, StagePlayer player, StageQuitReason reason) {
        super(stage);
        this.player = player;
        this.reason = reason;
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
    
    public StageQuitReason getReason() {
        return reason;
    }

}
