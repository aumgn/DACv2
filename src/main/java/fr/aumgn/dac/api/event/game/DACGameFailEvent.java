package fr.aumgn.dac.api.event.game;

import org.bukkit.event.HandlerList;

import com.sk89q.worldedit.Vector2D;

import fr.aumgn.dac.api.game.event.GameJumpFail;

public class DACGameFailEvent extends DACGamePlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACGameFailEvent(GameJumpFail gameFail) {
        super(gameFail);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    protected GameJumpFail getGameEvent() {
        return (GameJumpFail) gameEvent;
    }

    public Vector2D getPos() {
        return getGameEvent().getPos();
    }

}