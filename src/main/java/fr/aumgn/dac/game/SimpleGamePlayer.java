package fr.aumgn.dac.game;

import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.player.DACSimplePlayer;

public class SimpleGamePlayer extends DACSimplePlayer {

	public SimpleGamePlayer(Game stage, DACPlayer player) {
		super(
			player.getPlayer(),
			stage,
			player.getColor(),
			player.getStartLocation()
		);
	}
	
}
