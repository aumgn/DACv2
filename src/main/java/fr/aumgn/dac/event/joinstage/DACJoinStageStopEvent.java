package fr.aumgn.dac.event.joinstage;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.joinstage.JoinStage;

public class DACJoinStageStopEvent extends DACJoinStageEvent {

	private static final long serialVersionUID = 1L;
	private static final HandlerList handlers = new HandlerList();
	
	public DACJoinStageStopEvent(JoinStage joinStage) {
		super("DACJoinStageStopEvent", joinStage);
	}
	
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}
