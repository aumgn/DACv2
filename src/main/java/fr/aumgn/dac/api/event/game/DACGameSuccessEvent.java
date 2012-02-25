package fr.aumgn.dac.api.event.game;

import org.bukkit.event.HandlerList;

import com.sk89q.worldedit.Vector2D;

import fr.aumgn.dac.api.game.event.GameJumpSuccess;

public class DACGameSuccessEvent extends DACGamePlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACGameSuccessEvent(GameJumpSuccess success) {
        super(success);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    @Override
    protected GameJumpSuccess getGameEvent() {
        return (GameJumpSuccess) gameEvent;
    }
    
    public Vector2D getPos() {
        return getGameEvent().getPos();
    }
    
    public boolean isADAC() {
        return getGameEvent().isADAC();
    }

}