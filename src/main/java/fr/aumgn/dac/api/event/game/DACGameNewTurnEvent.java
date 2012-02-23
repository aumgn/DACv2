package fr.aumgn.dac.api.event.game;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.game.Game;

public class DACGameNewTurnEvent extends DACGameEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACGameNewTurnEvent(Game<?> game) {
        super(game);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
