package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.game.Game;

public class GameWin extends GameFinish {

	Iterable<String> ranking;

	public GameWin(Game game, Iterable<String> ranking) {
		super(game, FinishReason.Winner);
		this.ranking = ranking;
	}

	public Iterable<String> getRanking() {
		return ranking;
	}

}
