package fr.aumgn.dac.game;

import fr.aumgn.dac.joinstep.JoinStagePlayer;
import fr.aumgn.dac.player.DACSimplePlayer;

public class BasicGamePlayer extends DACSimplePlayer {

	public BasicGamePlayer(JoinStagePlayer player) {
		super(
			player.getPlayer(),
			player.getStage(),
			player.getColor(),
			player.getStartLocation()
		);
	}

}
