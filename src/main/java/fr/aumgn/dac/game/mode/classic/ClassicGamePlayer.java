package fr.aumgn.dac.game.mode.classic;

import fr.aumgn.dac.game.SimpleGamePlayer;
import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.player.DACPlayer;

public class ClassicGamePlayer extends SimpleGamePlayer {

	private int lives;
	private boolean mustConfirmate; 
	
	public ClassicGamePlayer(Game game, DACPlayer player, int index) {
		super(game, player, index);
		this.lives = 0;
		this.mustConfirmate = false;
	}

	@Override
	public String formatForList() {
		return super.formatForList() + " : " + lives + " vie(s)";
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
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
