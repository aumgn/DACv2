package fr.aumgn.dac2.event.player;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GamePlayer;
import fr.aumgn.dac2.shape.column.Column;

public class DACJumpSuccessEvent extends DACJumpEvent {

    private final Column column;

    public DACJumpSuccessEvent(Game game, GamePlayer player, Vector position, Column column) {
        super(game, player, position);
        this.column = column;
    }

    public Column getColumn() {
        return column;
    }
}
