package fr.aumgn.dac2.event.player;

import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GamePlayer;

public class DACJumpFailEvent extends DACJumpEvent {

    public DACJumpFailEvent(Game game, GamePlayer player) {
        super(game, player);
    }
}
