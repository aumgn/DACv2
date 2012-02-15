package fr.aumgn.dac.event.game;

import fr.aumgn.dac.event.DACEvent;
import fr.aumgn.dac.game.Game;

@SuppressWarnings("serial")
public class DACGameEvent extends DACEvent {
	
	private Game<?> game;
	
	public DACGameEvent(String name, Game<?> game) {
		super(name);
		this.game = game;
	}
	
	public Game<?> getGame() {
		return game;
	}
	
}
