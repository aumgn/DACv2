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
 * Responsible for managing all ongoing stages.
 * Also responsible for registering and 
 * unregistering listener when needed.
 */
public class StageManager implements Iterable<Stage> {

    private Map<Arena, Stage> stages;
    private int gameCount = 0;

    public StageManager() {
        stages = new HashMap<Arena, Stage>();
    }

    public boolean hasStage(Arena arena) {
        return stages.containsKey(arena);
    }

    /**
     * Gets the stage for the given arena. Returns null if no stage is ongoing. 
     */
    public Stage get(Arena arena) {
        return stages.get(arena);
    }

    /**
     * Gets the stage in which the player is. Return null if the player
     * is not in a stage.
     */
    public Stage get(Player player) {
        StagePlayer dacPlayer = DAC.getPlayerManager().get(player);
        if (dacPlayer == null) {
            return null;
        }
        return dacPlayer.getStage();
    }

    /**
     * Gets the game for the given arena.
     * <p/>
     * This is just a convenient method which cast the stage 
     * or return null if can't.
     * @see #get(Arena) 
     */
    public Game getGame(Arena arena) {
        return castGame(get(arena));
    }

    /**
     * Gets the game for the given player.
     * <p/>
     * This is just a convenient method which cast the stage 
     * or return null if can't.
     * @see #get(Player) 
     */
    public Game getGame(Player player) {
        return castGame(get(player));
    }

    private Game castGame(Stage stage) {
        if (stage instanceof Game) {
            return (Game) stage;
        }
        return null;
    }

    /**
     * Registers the given stage.
     * Also registers all associated players.
     */
    public void register(Stage stage) {
        if (stages.containsKey(stage.getArena())) {
            throw new StageAlreadyRegistered();
        }
        if (stage instanceof Game) {
            incrementGameCount();
        }
        stages.put(stage.getArena(), stage);
        stage.registerAll();
    }

    private void incrementGameCount() {
        gameCount++;
        if (gameCount == 1) {
            DAC.registerListener();
        }
    }

    /**
     * Unregisters the given stage.
     * Also unregisters all associated players.
     */
    public void unregister(Stage stage) {
        if (!stages.containsKey(stage.getArena())) {
            throw new StageNotRegistered();
        }
        if (stage instanceof Game) {
            decrementGameCount();
        }
        stages.remove(stage.getArena());
        stage.unregisterAll();
    }

    private void decrementGameCount() {
        gameCount--;
        if (gameCount == 0) {
            DAC.unregisterListener();
        }
    }

    @Override
    public Iterator<Stage> iterator() {
        return stages.values().iterator();
    }

}
