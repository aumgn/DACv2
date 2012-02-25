package fr.aumgn.dac.api.event.game;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.game.event.GameTurn;

public class DACGameTurnEvent extends DACGamePlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACGameTurnEvent(GameTurn turn) {
        super(turn);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}
