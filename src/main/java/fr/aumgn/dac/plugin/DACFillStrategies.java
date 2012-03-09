package fr.aumgn.dac.plugin;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.fillstrategy.DACFillStrategy;
import fr.aumgn.dac.api.fillstrategy.DACFillStrategyProvider;
import fr.aumgn.dac.api.fillstrategy.FillStrategies;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;

public class DACFillStrategies implements FillStrategies {

    private Map<String, FillStrategy> strategies = new HashMap<String, FillStrategy>();

    public DACFillStrategies() {
        for (Plugin bukkitPlugin : Bukkit.getPluginManager().getPlugins()) {
            if (bukkitPlugin instanceof DACFillStrategyProvider) {
                DACFillStrategyProvider strategiesProvider = (DACFillStrategyProvider) bukkitPlugin;
                for (Class<? extends FillStrategy> strategy : strategiesProvider.getFillStrategies()) {
                    register(bukkitPlugin, strategy);
                }
            }
        }
    }

    private void logRegisterError(Plugin plugin, Class<? extends FillStrategy> strategyCls, String specificError) {
        String pluginName = plugin.getDescription().getName();
        DAC.getLogger().warning("Cannot register fill strategy " + strategyCls.getSimpleName() + " from " + pluginName);
        DAC.getLogger().warning(specificError);
    }

    private void register(Plugin plugin, Class<? extends FillStrategy> strategyCls) {
        DACFillStrategy annotation = strategyCls.getAnnotation(DACFillStrategy.class);
        if (annotation == null) {
            logRegisterError(plugin, strategyCls, "Annotation `DACFillStrategy` missing");
            return;
        }

        String strategyName = annotation.name();
        if (strategies.containsKey(strategyName)) {
            logRegisterError(plugin, strategyCls, "Conflict with already registered strategy.");
            return;
        }

        try {
            FillStrategy strategy = strategyCls.newInstance();
            strategies.put(strategyName, strategy);
            for (String alias : annotation.aliases()) {
                if (!strategies.containsKey(alias)) {
                    strategies.put(alias, strategy);
                }
            }
        } catch (InstantiationException exc) {
            DAC.getLogger().warning("Cannot register fill strategy " + strategyName);
        } catch (IllegalAccessException e) {
            DAC.getLogger().warning("Cannot register fill strategy " + strategyName);
        }
    }

    @Override
    public FillStrategy get(String name) {
        return strategies.get(name);
    }

}
