package fr.aumgn.dac.api.game;

import fr.aumgn.dac.api.stage.SimpleStagePlayer;
import fr.aumgn.dac.api.stage.StagePlayer;

public class SimpleGamePlayer extends SimpleStagePlayer {
	
	private int index;
	
	public SimpleGamePlayer(Game<? extends SimpleGamePlayer> stage, StagePlayer player, int index) {
		super(
			player.getPlayer(),
			stage,
			player.getColor(),
			player.getStartLocation()
		);
		this.index = index;
	}
	
	@Override
	public String formatForList() {
		return " " + index + super.formatForList();
	}

	public int getIndex() {
		return index;
	}
	
}
