package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.game.Game;

public class GameFinish extends GameEvent {

	private FinishReason reason;
	private boolean poolReset;
	
	public GameFinish(Game game, FinishReason reason) {
		super(game);
		this.reason = reason;
		this.poolReset = DAC.getConfig().getResetOnEnd();
	}
	
	public FinishReason getReason() {
		return reason;
	}
	
	public boolean getPoolReset() {
		return poolReset;
	}
	
	public void setPoolReset(boolean poolReset) {
		this.poolReset = poolReset;
	}
	
	public enum FinishReason {
		Forced,
		NotEnoughPlayer,
		Winner
	}

}
