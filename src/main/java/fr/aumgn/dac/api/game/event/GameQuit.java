package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StageQuitReason;

public class GameQuit extends GameLoose {

	private StageQuitReason reason;
	
	public GameQuit(StagePlayer player, StageQuitReason reason) {
		super(player);
		this.reason = reason;
	}
	
	public StageQuitReason getReason() {
		return reason;
	}
	
}
