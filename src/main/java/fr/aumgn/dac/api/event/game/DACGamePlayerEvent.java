package fr.aumgn.dac.api.event.game;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StagePlayer;

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
