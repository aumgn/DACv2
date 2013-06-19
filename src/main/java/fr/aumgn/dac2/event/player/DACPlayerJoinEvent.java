package fr.aumgn.dac2.event.player;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac2.event.DACStagePlayerEvent;
import fr.aumgn.dac2.stage.join.JoinStage;
import fr.aumgn.dac2.stage.StagePlayer;

public abstract class DACPlayerJoinEvent extends DACStagePlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACPlayerJoinEvent(JoinStage stage, StagePlayer player) {
        super(stage, player);
    }

    public JoinStage getStage() {
        return (JoinStage) super.getStage();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
