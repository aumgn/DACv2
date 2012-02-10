package fr.aumgn.dac.event.joinstage;

import fr.aumgn.dac.joinstage.JoinStage;
import fr.aumgn.dac.player.DACPlayer;

@SuppressWarnings("serial")
public class DACJoinStagePlayerEvent extends DACJoinStageEvent {

	private DACPlayer player;
	
	public DACJoinStagePlayerEvent(String name, JoinStage joinStage, DACPlayer player) {
		super(name, joinStage);
		this.player = player;
	}

	public DACPlayer getPlayer() {
		return player;
	}
	
}
