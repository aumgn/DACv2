package fr.aumgn.dac.game.mode.suddendeath;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.SimpleGamePlayer;
import fr.aumgn.dac.player.DACPlayer;

public class SuddenDeathGamePlayer extends SimpleGamePlayer {

	private boolean deadThisTurn;
	private boolean dead;

	public SuddenDeathGamePlayer(Game<SuddenDeathGamePlayer> game, DACPlayer player, int index) {
		super(game, player, index);
		this.deadThisTurn = false;
		this.dead = false;
	}

	@Override
	public String formatForList() {
		return super.formatForList() + " : " + (dead ? "Mort" : "En vie");
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
