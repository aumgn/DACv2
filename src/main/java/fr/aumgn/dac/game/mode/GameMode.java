package fr.aumgn.dac.game.mode;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.player.DACPlayer;

public interface GameMode {

	GameModeHandler createHandler(Game game);
	
	DACPlayer createPlayer(Game game, DACPlayer player);
	
	int getMinimumPlayer();
	
}
