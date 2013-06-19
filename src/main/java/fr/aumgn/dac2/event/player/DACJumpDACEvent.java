package fr.aumgn.dac2.event.player;

import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GamePlayer;

public class DACJumpDACEvent extends DACJumpSuccessEvent {

    public DACJumpDACEvent(Game game, GamePlayer player) {
        super(game, player);
    }
}
