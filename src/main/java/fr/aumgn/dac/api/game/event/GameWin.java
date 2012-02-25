package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StagePlayer;

public class GameWin extends GameFinish {

	Iterable<StagePlayer> ranking;

	public GameWin(Game game, Iterable<StagePlayer> ranking) {
		super(game, FinishReason.Winner);
		this.ranking = ranking;
	}

	public Iterable<StagePlayer> getRanking() {
		return ranking;
	}

}
