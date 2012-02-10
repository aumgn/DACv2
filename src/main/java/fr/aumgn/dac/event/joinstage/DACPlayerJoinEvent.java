package fr.aumgn.dac.event.joinstage;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import fr.aumgn.dac.joinstage.JoinStage;
import fr.aumgn.dac.player.DACPlayer;

public class DACPlayerJoinEvent extends DACJoinStagePlayerEvent implements Cancellable {
	private static final long serialVersionUID = 1L;
	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled = false;
	
	public DACPlayerJoinEvent(JoinStage joinStage, DACPlayer player) {
		super("DACPlayerJoinEvent", joinStage, player);
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		isCancelled = cancelled;
	}

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}
