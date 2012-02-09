package fr.aumgn.dac.game.mode.default_;

import fr.aumgn.dac.game.SimpleGamePlayer;
import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.player.DACPlayer;

public class DefaultGamePlayer extends SimpleGamePlayer {

	private int lives;
	private boolean mustConfirmate; 
	
	public DefaultGamePlayer(Game game, DACPlayer player, int index) {
		super(game, player, index);
		this.lives = 0;
		this.mustConfirmate = false;
	}

	public int getLives() {
		return lives;
	}

	public boolean mustConfirmate() {
		return mustConfirmate;
	}

	public void resetLives() {
		lives = 0;
	}

	public void winLive() {
		lives++;
	}

	public void looseLive() {
		lives--;
	}

	public void looseAllLives() {
		lives = -1;
	}
	
	public boolean hasLost() {
		return lives == -1;
	}

	public void setMustConfirmate(boolean bool) {
		mustConfirmate = bool;
	}
}
