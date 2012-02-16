package fr.aumgn.dac.api.event.game;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.game.Game;

public class DACGameNewTurnEvent extends DACGameEvent {

	private static final long serialVersionUID = 1L;
	private static final HandlerList handlers = new HandlerList();
	
	public DACGameNewTurnEvent(Game<?> game) {
		super("DACGameNewTurnEvent", game);
	}
	
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}