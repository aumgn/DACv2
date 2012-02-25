package fr.aumgn.dac.api.event.game;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.game.event.GameWin;

public class DACGameWinEvent extends DACGameEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACGameWinEvent(GameWin win) {
        super(win);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    protected GameWin getGameEvent() {
        return (GameWin) gameEvent;
    }
    
    public Iterable<String> getRanking() {
        return getGameEvent().getRanking();
    }

}
