package fr.aumgn.dac.game.mode;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import fr.aumgn.dac.DAC;

public class DACGameModes {

	private Map<String, GameMode<?>> modes = new HashMap<String, GameMode<?>>();

	public DACGameModes() {
		for (Plugin bukkitPlugin : Bukkit.getPluginManager().getPlugins()) {
			if (bukkitPlugin instanceof DACGameModeProvider) {
				DACGameModeProvider gameModeProvider = (DACGameModeProvider)bukkitPlugin;
				for (Class<? extends GameMode<?>> gameMode : gameModeProvider.getGameModes()) {
					register(gameMode);
				}
			}
		}		
	}
	
	private void register(Class<? extends GameMode<?>> modeCls) {
		DACGameMode annotation = modeCls.getAnnotation(DACGameMode.class);
		if (annotation == null) {
			DAC.getLogger().warning("Cannot register game mode for " + modeCls.getSimpleName());
			DAC.getLogger().warning("Annotation `DACGameMode` missing");
			return;
		}
				
		String modeName = annotation.name();
		try {
			GameMode<?> mode = modeCls.newInstance();
			modes.put(modeName, mode);
		} catch (InstantiationException exc) {
			DAC.getLogger().warning("Cannot register game mode " + modeName);
		} catch (IllegalAccessException e) {
			DAC.getLogger().warning("Cannot register game mode " + modeName);
		}
	}

	public Set<String> getDefaults() {
		HashSet<String> set = new HashSet<String>();
		for (Map.Entry<String, GameMode<?>> modeEntry : modes.entrySet()) {
			if (modeEntry.getValue().getClass().getAnnotation(DACGameMode.class).isDefault()) {
				set.add(modeEntry.getKey());
			}
		}
		return set;
	}
	
	public Collection<String> getNames() {
		return modes.keySet();
	}
	
	public GameMode<?> get(String name) {
		return modes.get(name);
	}
	
}
