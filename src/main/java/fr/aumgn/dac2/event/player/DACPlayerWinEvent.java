package fr.aumgn.dac2.event.player;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac2.event.DACGamePlayerEvent;
import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GamePlayer;

public class DACPlayerWinEvent extends DACGamePlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACPlayerWinEvent(Game game, GamePlayer player) {
        super(game, player);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
