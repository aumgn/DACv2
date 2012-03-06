package fr.aumgn.dac.api.game.mode;

import fr.aumgn.dac.api.game.event.GameFinish;
import fr.aumgn.dac.api.game.event.GameJumpFail;
import fr.aumgn.dac.api.game.event.GameJumpSuccess;
import fr.aumgn.dac.api.game.event.GameLoose;
import fr.aumgn.dac.api.game.event.GameNewTurn;
import fr.aumgn.dac.api.game.event.GamePoolFilled;
import fr.aumgn.dac.api.game.event.GameStart;
import fr.aumgn.dac.api.game.event.GameTurn;

public class SimpleGameHandler implements GameHandler {

	@Override
	public void onStart(GameStart start) {
	}

	@Override
	public void onNewTurn(GameNewTurn newTurn) {
	}

	@Override
	public void onTurn(GameTurn turn) {
	}

	@Override
	public void onSuccess(GameJumpSuccess success) {
	}

	@Override
	public void onFail(GameJumpFail fail) {
	}

	@Override
	public void onLoose(GameLoose quit) {
	}

	@Override
	public void onFinish(GameFinish finished) {
	}
	
	@Override
	public void onPoolFilled(GamePoolFilled filled) {
	    
	}

}