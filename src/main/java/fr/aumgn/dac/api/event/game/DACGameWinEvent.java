package fr.aumgn.dac.api.event.game;

import java.util.Iterator;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.game.event.GameWin;
import fr.aumgn.dac.api.stage.StagePlayer;

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
    
    public StagePlayer getWinner() {
        Iterator<StagePlayer> ranking = getRanking().iterator(); 
        if (ranking.hasNext()) {
            return getRanking().iterator().next();
        } else {
            return null;
        }
    }

    public Iterable<StagePlayer> getRanking() {
        return getGameEvent().getRanking();
    }
    
}
