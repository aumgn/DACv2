package fr.aumgn.dac.event.game;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.player.DACPlayer;

public class DACGameFailEvent extends DACGamePlayerEvent implements Cancellable {

	private static final long serialVersionUID = 1L;
	private static final HandlerList handlers = new HandlerList();

	private boolean isCancelled = false;
	private boolean cancelDeath = true; 
	
	public DACGameFailEvent(Game<?> game, DACPlayer player) {
		super("DACGameFailEvent", game, player);
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

	public boolean cancelDeath() {
		return cancelDeath;
	}

	public void setCancelDeath(boolean cancelDeath) {
		this.cancelDeath = cancelDeath;
	}

}
