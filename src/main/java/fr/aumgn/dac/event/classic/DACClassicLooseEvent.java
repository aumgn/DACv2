package fr.aumgn.dac.event.classic;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.mode.classic.ClassicGamePlayer;

public class DACClassicLooseEvent extends DACClassicPlayerEvent {

	private static final long serialVersionUID = 1L;
	private static final HandlerList handlers = new HandlerList();

	public DACClassicLooseEvent(Game game, ClassicGamePlayer player) {
		super("DACClassicLooseEvent", game, player);
	}
	
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}
