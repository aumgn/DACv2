package fr.aumgn.dac.api.game.mode;

import java.util.List;

public interface DACGameModeProvider {
	
	List<Class<? extends GameMode<?>>> getGameModes();

}
