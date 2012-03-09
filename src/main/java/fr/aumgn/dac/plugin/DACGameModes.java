package fr.aumgn.dac.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.DACGameModeProvider;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.game.mode.GameModes;

public class DACGameModes implements GameModes {

    private final Set<String> modes;
    private final Map<String, Class<? extends GameMode>> modesByAlias;

    public DACGameModes() {
        modes = new HashSet<String>();
        modesByAlias = new HashMap<String, Class<? extends GameMode>>();
        for (Plugin bukkitPlugin : Bukkit.getPluginManager().getPlugins()) {
            if (bukkitPlugin instanceof DACGameModeProvider) {
                DACGameModeProvider gameModeProvider = (DACGameModeProvider) bukkitPlugin;
                for (Class<? extends GameMode> gameMode : gameModeProvider.getGameModes()) {
                    register(bukkitPlugin, gameMode);
                }
            }
        }
    }

    private void logRegisterError(Plugin plugin, Class<? extends GameMode> modeCls, String specificError) {
        DAC.getLogger().warning("Cannot register game mode for " + modeCls.getSimpleName());
        DAC.getLogger().warning(specificError);
    }

    private void register(Plugin plugin, Class<? extends GameMode> mode) {
        DACGameMode annotation = mode.getAnnotation(DACGameMode.class);
        if (annotation == null) {
            logRegisterError(plugin, mode, "Annotation `DACGameMode` missing");
            return;
        }

        String modeName = annotation.name();
        if (modes.contains(modeName)) {
            logRegisterError(plugin, mode, "Conflict with already registered mode");
        }
        
        try {
            Class<?>[] params = null;
            mode.getConstructor(params);
        } catch (SecurityException exc) {
            logRegisterError(plugin, mode, "Empty constructor is not public");
            return;
        } catch (NoSuchMethodException exc) {
            logRegisterError(plugin, mode, "Do not include an empty constructor");
            return;
        }

        modes.add(modeName);
        modesByAlias.put(modeName, mode);
        for (String alias : annotation.aliases()) {
            modesByAlias.put(alias, mode);
        }
    }

    @Override
    public Collection<String> getDefaults() {
        List<String> list = new ArrayList<String>();
        for (String modeName : modes) {
            Class<? extends GameMode> mode = modesByAlias.get(modeName);
            if (mode.getAnnotation(DACGameMode.class).isDefault()) {
                list.add(modeName);
            }
        }
        return list;
    }

    @Override
    public Collection<String> getNames() {
        return modes;
    }

    @Override
    public GameMode getNewInstance(String name) {
        Class<? extends GameMode> mode = modesByAlias.get(name);
        if (mode == null) {
            return null;
        }
        /* Should never really fail. */
        try {
            return mode.newInstance();
        } catch (InstantiationException exc) {            
            return null;
        } catch (IllegalAccessException exc) {
            return null;
        }
    }

}
