package fr.aumgn.dac2.event.player;

import fr.aumgn.dac2.event.DACStagePlayerEvent;
import fr.aumgn.dac2.stage.StagePlayer;
import fr.aumgn.dac2.stage.join.JoinStage;
import org.bukkit.event.HandlerList;

public class DACPlayerJoinEvent extends DACStagePlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACPlayerJoinEvent(JoinStage stage, StagePlayer player) {
        super(stage, player);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public JoinStage getStage() {
        return (JoinStage) super.getStage();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
