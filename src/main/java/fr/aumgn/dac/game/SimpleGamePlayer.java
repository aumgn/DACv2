package fr.aumgn.dac.game;

import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.player.DACSimplePlayer;

public class SimpleGamePlayer extends DACSimplePlayer {
	
	private int index;
	
	public SimpleGamePlayer(Game<? extends SimpleGamePlayer> stage, DACPlayer player, int index) {
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
