package fr.aumgn.dac.api.game.mode;

import fr.aumgn.dac.api.stage.StagePlayer;

public interface GameModeHandler<T extends StagePlayer> {
	
	void onStart();

	void onNewTurn();

	void onTurn(T player);
	
	void onSuccess(T player);

	void onFail(T player);
	
	void onQuit(T player);
	
	void onStop();

}
