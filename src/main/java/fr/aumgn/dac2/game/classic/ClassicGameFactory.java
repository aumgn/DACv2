package fr.aumgn.dac2.game.classic;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GameFactory;
import fr.aumgn.dac2.stage.JoinStage;

public class ClassicGameFactory extends GameFactory {

    @Override
    public Game createGame(DAC dac, JoinStage joinStage) {
        return new ClassicGame(dac, joinStage);
    }
}
