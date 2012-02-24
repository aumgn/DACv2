package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.stage.StagePlayer;

public class GameLoose extends GamePlayerEvent {

	public GameLoose(StagePlayer player) {
		super(player);
	}

}
