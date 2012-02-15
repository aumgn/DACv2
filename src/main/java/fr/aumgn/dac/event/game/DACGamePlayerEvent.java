package fr.aumgn.dac.event.game;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.player.DACPlayer;

@SuppressWarnings("serial")
public class DACGamePlayerEvent extends DACGameEvent {

	private DACPlayer player;
	
	public DACGamePlayerEvent(String name, Game<?> game, DACPlayer player) {
		super(name, game);
		this.player = player;
	}

	public DACPlayer getPlayer() {
		return player;
	}
	
}
