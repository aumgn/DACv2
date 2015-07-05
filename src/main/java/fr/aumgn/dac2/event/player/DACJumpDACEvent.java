package fr.aumgn.dac2.event.player;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GamePlayer;
import fr.aumgn.dac2.shape.column.Column;

public class DACJumpDACEvent extends DACJumpSuccessEvent {

    public DACJumpDACEvent(Game game, GamePlayer player, Vector position, Column column) {
        super(game, player, position, column);
    }
}
