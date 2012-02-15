package fr.aumgn.dac.event.game;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.stage.StagePlayer;

public class DACGameSuccessEvent extends DACGamePlayerEvent implements Cancellable {

	private static final long serialVersionUID = 1L;
	private static final HandlerList handlers = new HandlerList();

	private boolean isCancelled = false;
	
	public DACGameSuccessEvent(Game<?> game, StagePlayer player) {
		super("DACGameSuccessEvent", game, player);
	}
	
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		isCancelled = cancelled;
	}

}
