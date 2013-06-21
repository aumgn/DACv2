package fr.aumgn.dac2.event.player;

import org.bukkit.event.HandlerList;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.event.DACGamePlayerEvent;
import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GamePlayer;

public abstract class DACJumpEvent extends DACGamePlayerEvent {

    private static final HandlerList handlers = new HandlerList();
    private final Vector position;

    public DACJumpEvent(Game game, GamePlayer player) {
        super(game, player);
        this.position = new Vector(player.getRef().getPlayer());
    }

    public Vector getPosition() {
        return position;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
