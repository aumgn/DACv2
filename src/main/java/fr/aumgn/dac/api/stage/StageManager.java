package fr.aumgn.dac.api.stage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.game.Game;

public class StageManager implements Iterable<Stage<?>>{

    private Map<Arena, Stage<?>> stages;

    public StageManager() {
        stages = new HashMap<Arena, Stage<?>>();
    }

    public boolean hasStage(Arena arena) {
        return stages.containsKey(arena);
    }

    public Stage<?> get(Arena arena) {
        return stages.get(arena);
    }

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

    public Game<?> getGame(Arena arena) {
        return castGame(get(arena));
    }

    public Game<?> getGame(Player player) {
        return castGame(get(player));
    }

    public void register(Stage<?> stage) {
        if (stages.containsKey(stage.getArena())) {
            throw new RuntimeException();
        }
        stages.put(stage.getArena(), stage);
        stage.registerAll();
    }

    public void unregister(Stage<?> stage) {
        if (!stages.containsKey(stage.getArena())) {
            throw new RuntimeException();
        }
        stages.remove(stage.getArena());
        stage.unregisterAll();
    }

    @Override
    public Iterator<Stage<?>> iterator() {
        return stages.values().iterator();
    }

}
