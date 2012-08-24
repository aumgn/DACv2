package fr.aumgn.dac2.game;

import java.util.HashMap;
import java.util.Map;

import fr.aumgn.dac2.DAC;
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
}
