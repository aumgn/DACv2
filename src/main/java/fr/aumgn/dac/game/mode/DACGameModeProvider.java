package fr.aumgn.dac.game.mode;

import java.util.List;

public interface DACGameModeProvider {
	
	List<Class<? extends GameMode<?>>> getGameModes();
	
}
