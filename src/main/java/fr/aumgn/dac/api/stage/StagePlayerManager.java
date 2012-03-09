package fr.aumgn.dac.api.stage;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.exception.PlayerAlreadyRegistered;
import fr.aumgn.dac.api.exception.PlayerNotRegistered;

/**
 * Class responsible for managing all player in stages. 
 */
public class StagePlayerManager {

    private Map<Player, StagePlayer> players;

    public StagePlayerManager() {
        players = new HashMap<Player, StagePlayer>();
    }

    /**
     * Gets the wrapped {@link StagePlayer} for the given player.
     */
    public StagePlayer get(Player player) {
        return players.get(player);
    }

    /**
     * Registers the given player.
     * 
     * @throws PlayerAlreadyRegistered if soft is false and player is already registered  
     * @param soft indicates that no exception should be raised 
     *          if player is already registered.  
     */
    public void register(StagePlayer player, boolean soft) {
        if (!soft && players.containsKey(player.getPlayer())) {
            throw new PlayerAlreadyRegistered();
        }
        players.put(player.getPlayer(), player);
    }

    /**
     * @see #register(StagePlayer, boolean)
     */
    public void register(StagePlayer player) {
        register(player, false);
    }

    /**
     * Unregister the given player.
     * 
     * @throws PlayerNotRegistered if soft is false and player is not registered
     * @param soft indicates that no exception should be raised 
     *          if player is not registered.  
     */
    public void unregister(StagePlayer player, boolean soft) {
        if (!soft && !players.containsKey(player.getPlayer())) {
            throw new PlayerNotRegistered();
        }
        players.remove(player.getPlayer());
    }

    /**
     * @see #unregister(StagePlayer, boolean)
     */
    public void unregister(StagePlayer player) {
        unregister(player, false);
    }

}
