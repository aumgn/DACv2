package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StageStopReason;

public class GameWin extends GameFinish {

    Iterable<StagePlayer> ranking;

    public GameWin(Game game, Iterable<StagePlayer> ranking) {
        super(game, StageStopReason.Winner);
        this.ranking = ranking;
    }

    public Iterable<StagePlayer> getRanking() {
        return ranking;
    }

}
