package fr.aumgn.dac2.game;

import java.util.HashMap;
import java.util.Map;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.game.classic.ClassicGame;
import fr.aumgn.dac2.game.training.Training;
import fr.aumgn.dac2.stage.JoinStage;

public abstract class GameFactory {

    private static Map<String, GameFactory> factories =
            new HashMap<String, GameFactory>();
    private static Map<String, GameFactory> byAliases =
            new HashMap<String, GameFactory>();

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

    public static GameFactory getByAlias(String alias) {
        return byAliases.get(alias);
    }

    public int getMinimumPlayers() {
        return 2;
    }

    public abstract Game createGame(DAC dac, JoinStage joinStage);

    static {
        GameFactory.register("classic", new GameFactory() {

            @Override
            public Game createGame(DAC dac, JoinStage joinStage) {
                return new ClassicGame(dac, joinStage);
            }
        }, "cl", "default", "def");

        GameFactory.register("training", new GameFactory() {

            @Override
            public int getMinimumPlayers() {
                return 1;
            }

            @Override
            public Game createGame(DAC dac, JoinStage joinStage) {
                return new Training(dac, joinStage);
            }
        }, "t", "tr");
    }
}
