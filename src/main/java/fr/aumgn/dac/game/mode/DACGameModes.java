package fr.aumgn.dac.game.mode;

import java.util.HashMap;
import java.util.Map;

import fr.aumgn.dac.DAC;

public final class DACGameModes {
	
	private static Map<String, GameMode> modes = new HashMap<String, GameMode>();
	
	private DACGameModes() {}
	
	public static GameMode get(String name) {
		return modes.get(name);
	}
	
	public static void register(Class<? extends GameMode> modeCls) {
		DACGameMode annotation = modeCls.getAnnotation(DACGameMode.class);
		if (annotation == null) {
			DAC.getLogger().warning("Cannot regitser game mode for " + modeCls.getSimpleName());
			DAC.getLogger().warning("Annotation `DACGameMode` missing");
			return;
		}
				
		String modeName = annotation.value();
		try {
			GameMode mode = modeCls.newInstance();
			modes.put(modeName, mode);
		} catch (InstantiationException exc) {
			DAC.getLogger().warning("Cannot register game mode " + modeName);
		} catch (IllegalAccessException e) {
			DAC.getLogger().warning("Cannot register game mode " + modeName);
		}
	}
	
}
