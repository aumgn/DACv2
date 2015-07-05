package fr.aumgn.dac2.event.player;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.event.DACGamePlayerEvent;
import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GamePlayer;
import org.bukkit.event.HandlerList;

public abstract class DACJumpEvent extends DACGamePlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Vector position;

    public DACJumpEvent(Game game, GamePlayer player, Vector position) {
        super(game, player);
        this.position = position;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Vector getPosition() {
        return position;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
