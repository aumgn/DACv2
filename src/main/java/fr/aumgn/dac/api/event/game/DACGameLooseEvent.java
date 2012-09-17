package fr.aumgn.dac.api.event.game;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.game.event.GameLoose;

public class DACGameLooseEvent extends DACGamePlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACGameLooseEvent(GameLoose loose) {
        super(loose);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
