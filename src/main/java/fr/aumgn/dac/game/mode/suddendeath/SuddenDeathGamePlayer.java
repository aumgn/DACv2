package fr.aumgn.dac.game.mode.suddendeath;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.SimpleGamePlayer;
import fr.aumgn.dac.player.DACPlayer;

public class SuddenDeathGamePlayer extends SimpleGamePlayer {

	private boolean deadThisTurn;
	private boolean dead;

	public SuddenDeathGamePlayer(Game game, DACPlayer player, int index) {
		super(game, player, index);
		this.deadThisTurn = false;
		this.dead = false;
	}

	public boolean isDeadThisTurn() {
		return deadThisTurn;
	}
	
	public void setDeadThisTurn() {
		deadThisTurn = true;
	}
	
	public void cancelDeadThisTurn() {
		deadThisTurn = false;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void setDead() {
		deadThisTurn = false;
		dead = true;
	}
		

}
