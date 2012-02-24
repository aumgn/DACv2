package fr.aumgn.dac.api.event.game;

import org.bukkit.event.HandlerList;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StagePlayer;

public class DACGameLooseEvent extends DACGamePlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    public DACGameLooseEvent(Game game, StagePlayer player) {
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
