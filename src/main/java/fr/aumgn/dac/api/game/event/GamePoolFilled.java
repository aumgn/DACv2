package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.stage.StagePlayer;

public class GamePoolFilled extends GamePlayerEvent {

    public GamePoolFilled(StagePlayer player) {
        super(player);
    }

}
