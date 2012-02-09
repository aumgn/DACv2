package fr.aumgn.dac.game.mode;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.player.DACPlayer;

public interface GameMode {

	GameModeHandler createHandler(Game Game);
	
	DACPlayer createPlayer(DACPlayer player);
	
}
