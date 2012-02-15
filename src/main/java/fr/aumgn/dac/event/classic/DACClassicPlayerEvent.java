package fr.aumgn.dac.event.classic;

import fr.aumgn.dac.event.game.DACGameEvent;
import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.mode.classic.ClassicGamePlayer;

@SuppressWarnings("serial")
public class DACClassicPlayerEvent extends DACGameEvent {

	private ClassicGamePlayer player;
	
	public DACClassicPlayerEvent(String name, Game<?> game, ClassicGamePlayer player) {
		super(name, game);
		this.player = player;
	}

	public ClassicGamePlayer getPlayer() {
		return player;
	}
	
}
