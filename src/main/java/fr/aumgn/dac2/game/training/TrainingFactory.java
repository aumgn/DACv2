package fr.aumgn.dac2.game.training;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GameFactory;
import fr.aumgn.dac2.stage.JoinStage;

public class TrainingFactory extends GameFactory {

    @Override
    public int getMinimumPlayers() {
        return 1;
    }

    @Override
    public Game createGame(DAC dac, JoinStage joinStage) {
        return new Training(dac, joinStage);
    }
}
