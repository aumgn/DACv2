package fr.aumgn.dac2.event.player;

import fr.aumgn.dac2.event.DACGamePlayerEvent;
import fr.aumgn.dac2.game.Game;
import fr.aumgn.dac2.game.GamePlayer;
import org.bukkit.event.HandlerList;

public class DACPlayerWinEvent extends DACGamePlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACPlayerWinEvent(Game game, GamePlayer player) {
        super(game, player);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
