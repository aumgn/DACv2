package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.stage.StagePlayer;

public class GameTurn extends GamePlayerEvent {

	private boolean teleport;
	
	public GameTurn(StagePlayer player) {
		super(player);
		teleport = true;
	}
	
	public boolean getTeleport() {
		return teleport;
	}
	
	public void setTeleport(boolean teleport) {
		this.teleport = teleport;
	}

}
