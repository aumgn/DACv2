package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.stage.StagePlayer;

public class GameQuit extends GameLoose {

	private QuitReason reason;
	
	public GameQuit(StagePlayer player, QuitReason reason) {
		super(player);
		this.reason = reason;
	}
	
	public QuitReason getReason() {
		return reason;
	}
	
	public enum QuitReason {
		DISCONNECTED,
		COMMAND,
		KICKED
	}

}
