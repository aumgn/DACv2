package fr.aumgn.dac2.event.player;

import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GamePlayer;

public class DACJumpSuccessEvent extends DACJumpEvent {

    public DACJumpSuccessEvent(Game game, GamePlayer player) {
        super(game, player);
    }
}
