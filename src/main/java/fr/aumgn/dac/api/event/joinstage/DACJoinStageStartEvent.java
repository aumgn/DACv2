package fr.aumgn.dac.api.event.joinstage;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.joinstage.JoinStage;

public class DACJoinStageStartEvent extends DACJoinStageEvent {

    private static final long serialVersionUID = 1L;
    private static final HandlerList handlers = new HandlerList();

    public DACJoinStageStartEvent(JoinStage<?> joinStage) {
        super("DACJoinStageStartEvent", joinStage);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
