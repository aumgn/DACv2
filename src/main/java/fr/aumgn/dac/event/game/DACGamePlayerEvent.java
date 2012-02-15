package fr.aumgn.dac.event.game;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.stage.StagePlayer;

@SuppressWarnings("serial")
public class DACGamePlayerEvent extends DACGameEvent {

	private StagePlayer player;
	
	public DACGamePlayerEvent(String name, Game<?> game, StagePlayer player) {
		super(name, game);
		this.player = player;
	}

	public StagePlayer getPlayer() {
		return player;
	}
	
}
