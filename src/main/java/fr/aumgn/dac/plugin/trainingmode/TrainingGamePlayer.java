package fr.aumgn.dac.plugin.trainingmode;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.SimpleGamePlayer;
import fr.aumgn.dac.api.stage.StagePlayer;

public class TrainingGamePlayer extends SimpleGamePlayer {

	private boolean playing = true;
	private int successes = 0;
	private int dacs = 0;
	private int fails = 0;
	
	public TrainingGamePlayer(Game<TrainingGamePlayer> stage, StagePlayer player, int index) {
		super(stage, player, index);
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	
	public int getSuccesses() {
		return successes;
	}

	public void incrementSuccesses() {
		this.successes++;
	}

	public int getDACs() {
		return dacs;
	}

	public void incrementDACs() {
		this.dacs++;
	}

	public int getFails() {
		return fails;
	}

	public void incrementFails() {
		this.fails++;
	}

}
