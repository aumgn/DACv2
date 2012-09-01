package fr.aumgn.dac2.game;

import java.util.HashMap;
import java.util.Map;


import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.exceptions.UnknownGameType;
import fr.aumgn.dac2.game.classic.ClassicGame;
import fr.aumgn.dac2.game.colonnisation.Colonnisation;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.game.training.Training;

/**
 * Manages all different game modes
 */
public abstract class GameFactory {

    private static Map<String, GameFactory> factories =
            new HashMap<String, GameFactory>();
    private static Map<String, GameFactory> byAliases =
            new HashMap<String, GameFactory>();

    /**
     * Registers a GameFactory with the given name & aliases.
     *
     * @param name the name
     * @param factory the factory
     * @param aliases the aliases.
     */
    public static void register(String name, GameFactory factory,
            String... aliases) {
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
     * @param dac the main {@link DAC} instance
     * @param alias the alias to look for.
     * @throws UnknownGameType if the game factory can't be found.
     * @return the factory in question
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
     *
     * @return the minimum required number of player
     */
    public int getMinimumPlayers() {
        return 2;
    }

    /**
     * Factory method which creates the game based on the given data.
     */
    public abstract Game createGame(DAC dac, GameStartData data);

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
    }
}
