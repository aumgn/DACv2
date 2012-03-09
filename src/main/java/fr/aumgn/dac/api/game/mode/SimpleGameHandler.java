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
    public void onStart(GameStart event) {
    }

    @Override
    public void onNewTurn(GameNewTurn event) {
    }

    @Override
    public void onTurn(GameTurn event) {
    }

    @Override
    public void onSuccess(GameJumpSuccess event) {
    }

    @Override
    public void onFail(GameJumpFail event) {
    }

    @Override
    public void onLoose(GameLoose event) {
    }

    @Override
    public void onPoolFilled(GamePoolFilled event) {
    }

    @Override
    public void onFinish(GameFinish event) {
    }

}