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
     *
     * @param player The player.
     * @return true if it's player turn, false otherwise.
     */
    boolean isPlayerTurn(Player player);

    /**
     * Callback called when a player succeed.
     *
     * @param player The player in question.
     */
    void onJumpSuccess(Player player);

    /**
     * Callback called when a player failed.
     *
     * @param player The player in question.
     */
    void onJumpFail(Player player);

    /**
     * Callback called when a player quit the server.
     *
     * @param player The player in question.
     */
    void onQuit(Player player);

    /**
     * Callback called when a new turn starts.
     * (ie. all players have jumped once since the last one)
     */
    void onNewTurn();
}
