package fr.aumgn.dac.api.event.joinstage;

import fr.aumgn.dac.api.event.DACEvent;
import fr.aumgn.dac.api.joinstage.JoinStage;

@SuppressWarnings("serial")
public class DACJoinStageEvent extends DACEvent {
	
	private JoinStage<?> joinStage;
	
	public DACJoinStageEvent(String name, JoinStage<?> joinStage) {
		super(name);
		this.joinStage = joinStage;
	}
	
	public JoinStage<?> getGame() {
		return joinStage;
	}
	
}
