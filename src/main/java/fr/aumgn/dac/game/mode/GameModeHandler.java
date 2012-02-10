package fr.aumgn.dac.game.mode;

import fr.aumgn.dac.player.DACPlayer;

public interface GameModeHandler {
	
	void onStart();

	void onNewTurn();

	void onTurn(DACPlayer player);
	
	void onSuccess(DACPlayer player);

	void onFail(DACPlayer player);
	
	void onQuit(DACPlayer player);

}
