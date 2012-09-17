package fr.aumgn.dac.api.fillstrategy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.aumgn.dac.api.DAC;

/**
 * Class which manages available {@link FillStrategy}.   
 */
public final class FillStrategies {

    private Set<String> strategies;
    private Map<String, FillStrategy> strategiesByAlias;

    public FillStrategies() {
        this.strategies = new HashSet<String>();
        this.strategiesByAlias = new HashMap<String, FillStrategy>();
    }

    public void register(FillStrategy fillStrategy, String name, String... aliases) {
        if (strategies.contains(name)) {
            DAC.getLogger().warning("Cannot register fill strategy " + name);
            DAC.getLogger().warning("Conflict with an already registered strategy");
            return;
        }
        strategies.add(name);
        strategiesByAlias.put(name, fillStrategy);
        for (String alias : aliases) {
            if (!strategiesByAlias.containsKey(alias)) {
                strategiesByAlias.put(alias, fillStrategy);
            }
        }
    }

    public FillStrategy get(String name) {
        return strategiesByAlias.get(name);
    }

}