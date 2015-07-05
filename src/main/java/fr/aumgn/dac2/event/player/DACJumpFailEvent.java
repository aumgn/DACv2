package fr.aumgn.dac2.event.player;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GamePlayer;

public class DACJumpFailEvent extends DACJumpEvent {

    public DACJumpFailEvent(Game game, GamePlayer player, Vector position) {
        super(game, player, position);
    }
}
