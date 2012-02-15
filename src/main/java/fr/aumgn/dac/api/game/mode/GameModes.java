package fr.aumgn.dac.api.game.mode;

import java.util.Collection;
import java.util.Set;

public interface GameModes {

	Set<String> getDefaults();
	
	Collection<String> getNames();
	
	GameMode<?> get(String name);
	
}
