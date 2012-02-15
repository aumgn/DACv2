package fr.aumgn.dac.game.mode;

import fr.aumgn.dac.stage.StagePlayer;

public interface GameModeHandler<T extends StagePlayer> {
	
	void onStart();

	void onNewTurn();

	void onTurn(T player);
	
	void onSuccess(T player);

	void onFail(T player);
	
	void onQuit(T player);
	
	void onStop();

}
