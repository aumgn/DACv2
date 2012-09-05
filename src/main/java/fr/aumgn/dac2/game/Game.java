package fr.aumgn.dac2.game;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aumgn.dac2.stage.Stage;

public interface Game extends Stage {

    /**
     * Check if it's the given player turn.
     *
     * This is used by {@link GameListener} to check if events need to be
     * processed. So this method should be optimized as much as possible
     * because some events (like {@link PlayerMoveEvent}) are heavy.
     */
    boolean isPlayerTurn(Player player);

    /**
     * Callback called when a player succeed.
     */
    void onJumpSuccess(Player player);

    /**
     * Callback called when a player failed.
     */
    void onJumpFail(Player player);

    /**
     * Callback called when a new turn starts.
     * (ie. all players have jumped once since the last one)
     */
    void onNewTurn();
}
