package fr.aumgn.dac.api.stage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.exception.StageAlreadyRegistered;
import fr.aumgn.dac.api.exception.StageNotRegistered;
import fr.aumgn.dac.api.game.Game;

/**
 * Class responsible for managing all ongoing stages 
 */
public class StageManager implements Iterable<Stage<?>> {

    private Map<Arena, Stage<?>> stages;

    public StageManager() {
        stages = new HashMap<Arena, Stage<?>>();
    }

    /**
     * Checks if there's an ongoing stage inside the given arena.
     *   
     * @param arena the arena to check for an ongoing stage
     * @return whether or not there's an ongoing stage for this arena
     */
    public boolean hasStage(Arena arena) {
        return stages.containsKey(arena);
    }

    /**
     * Gets the stage for the given arena. Returns null if no stage is ongoing. 
     *  
     * @param arena the arena
     * @return the stage for the given arena or null if none.
     */
    public Stage<?> get(Arena arena) {
        return stages.get(arena);
    }

    /**
     * Gets the stage in which the player is. Return null if the player
     * is not in a stage.
     * 
     * @param player the player
     * @return the stage for this player
     */
    public Stage<?> get(Player player) {
        StagePlayer dacPlayer = DAC.getPlayerManager().get(player);
        if (dacPlayer == null) {
            return null;
        }
        return dacPlayer.getStage();
    }

    private Game<?> castGame(Stage<?> stage) {
        if (stage instanceof Game) {
            return (Game<?>) stage;
        }
        return null;
    }

    /**
     * Gets the game for the given arena.
     * <p/>
     * This is just a convenient method which cast the stage 
     * or return null if can't.
     * @see #get(Arena) 
     */
    public Game<?> getGame(Arena arena) {
        return castGame(get(arena));
    }

    /**
     * Gets the game for the given player.
     * <p/>
     * This is just a convenient method which cast the stage 
     * or return null if can't.
     * @see #get(Player) 
     */
    public Game<?> getGame(Player player) {
        return castGame(get(player));
    }

    /**
     * Register the given stage.
     * It also register all associated players.
     * 
     * @param stage the stage to register 
     */
    public void register(Stage<?> stage) {
        if (stages.containsKey(stage.getArena())) {
            throw new StageAlreadyRegistered();
        }
        stages.put(stage.getArena(), stage);
        stage.registerAll();
    }
    
    /**
     * Unregister the given stage.
     * It also unregister all associated players.
     * 
     * @param stage the stage to unregister 
     */
    public void unregister(Stage<?> stage) {
        if (!stages.containsKey(stage.getArena())) {
            throw new StageNotRegistered();
        }
        stages.remove(stage.getArena());
        stage.unregisterAll();
    }

    /**
     * Gets an iterator over the current stages.  
     */
    @Override
    public Iterator<Stage<?>> iterator() {
        return stages.values().iterator();
    }

}
