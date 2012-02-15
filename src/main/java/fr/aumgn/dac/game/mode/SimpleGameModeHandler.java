package fr.aumgn.dac.game.mode;

import fr.aumgn.dac.stage.StagePlayer;

public class SimpleGameModeHandler<T extends StagePlayer> implements GameModeHandler<T> {

	@Override
	public void onStart() {
	}

	@Override
	public void onNewTurn() {
	}

	@Override
	public void onTurn(T player) {
	}

	@Override
	public void onSuccess(T player) {
	}

	@Override
	public void onFail(T player) {
	}
	
	@Override
	public void onQuit(T player) {
	}

	@Override
	public void onStop() {
	}

}
