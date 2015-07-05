package fr.aumgn.dac2.game;

import com.google.common.collect.Maps;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.exceptions.UnknownGameType;
import fr.aumgn.dac2.game.classic.ClassicGame;
import fr.aumgn.dac2.game.colonnisation.Colonnisation;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.game.suddendeath.SuddenDeath;
import fr.aumgn.dac2.game.training.Training;

import java.util.Map;

/**
 * Manages all different game modes
 */
public abstract class GameFactory {

    private static Map<String, GameFactory> factories = Maps.newHashMap();
    private static Map<String, GameFactory> byAliases = Maps.newHashMap();

    static {
        GameFactory.register("classic", new GameFactory() {

            @Override
            public Game createGame(DAC dac, GameStartData data) {
                return new ClassicGame(dac, data);
            }
        }, "cl", "default", "def");

        GameFactory.register("training", new GameFactory() {

            @Override
            public int getMinimumPlayers() {
                return 1;
            }

            @Override
            public Game createGame(DAC dac, GameStartData data) {
                return new Training(dac, data);
            }
        }, "tr", "t");

        GameFactory.register("colonnisation", new GameFactory() {

            @Override
            public Game createGame(DAC dac, GameStartData data) {
                return new Colonnisation(dac, data);
            }
        }, "col", "c");

        GameFactory.register("suddendeath", new GameFactory() {

            @Override
            public Game createGame(DAC dac, GameStartData data) {
                return new SuddenDeath(dac, data);
            }
        }, "sd");
    }

    /**
     * Registers a GameFactory with the given name & aliases.
     */
    public static void register(String name, GameFactory factory, String... aliases) {
        factories.put(name, factory);

        byAliases.put(name, factory);
        for (String alias : aliases) {
            byAliases.put(alias, factory);
        }
    }

    public static GameFactory get(String name) {
        return factories.get(name);
    }

    /**
     * Gets the game factory registered for the given alias.
     *
     * @throws UnknownGameType if the game factory can't be found.
     */
    public static GameFactory getByAlias(DAC dac, String alias) {
        if (!byAliases.containsKey(alias)) {
            throw new UnknownGameType(dac, alias);
        }
        return byAliases.get(alias);
    }

    /**
     * The minimum required number of player to start
     * a new game for this factory.
     */
    public int getMinimumPlayers() {
        return 2;
    }

    /**
     * Factory method which creates the game based on the given data.
     */
    public abstract Game createGame(DAC dac, GameStartData data);
}
