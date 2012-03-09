package fr.aumgn.dac.api.game;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.Stage;

/**
 * Represents a running dé a coudre game. 
 */
public interface Game extends Stage {

    GameMode getMode();

    GameOptions getOptions();

    public boolean canWatch(Player player);

    /**
     * Adds a spectator to this game.
     * <p/>
     * Returns false if player was already watching game, true otherwise.
     */
    boolean addSpectator(Player player);

    /**
     * Removes a spectator to this game.
     * <p/>
     * Returns true if player was not watching game, false otherwise.
     */
    boolean removeSpectator(Player player);

    /**
     * Sends the given message to all spectators prefixed with the arena's name.
     */
    void sendToSpectators(String message);

    void onFallDamage(EntityDamageEvent event);

    void onMove(PlayerMoveEvent event);

}