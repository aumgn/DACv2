package fr.aumgn.dac.plugin.mode.suddendeath;

import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.fillstrategy.defaults.FillAllButOne;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.event.GameJumpFail;
import fr.aumgn.dac.api.game.event.GameJumpSuccess;
import fr.aumgn.dac.api.game.event.GameLoose;
import fr.aumgn.dac.api.game.event.GameNewTurn;
import fr.aumgn.dac.api.game.event.GameTurn;
import fr.aumgn.dac.api.game.mode.SimpleGameHandler;

public class SuddenDeathGameHandler extends SimpleGameHandler {
	

    @Override
    public void onNewTurn(GameNewTurn newTurn) {
    	SuddenDeathGamePlayer lastPlayer = null;
    	Game game = newTurn.getGame();
        Iterable<SuddenDeathGamePlayer> players = newTurn.getPlayers(SuddenDeathGamePlayer.class);
        int playersLeftCount = game.getPlayers().size();
        for (SuddenDeathGamePlayer player : players) {
            if (player.isDeadThisTurn()) {
                playersLeftCount--;
            } else {
            	lastPlayer = player;
            }
        }
        if (playersLeftCount == 0) {
            for (SuddenDeathGamePlayer player : players) {
                if (player.isDeadThisTurn()) {
                    player.cancelDeadThisTurn();
                }
            }
        } else if (playersLeftCount == 1) {
            newTurn.send(DACMessage.GameWinner, lastPlayer.getDisplayName());
            game.onWin(lastPlayer);
            return;
        } else {
            for (SuddenDeathGamePlayer player : players) {
                if (player.isDeadThisTurn()) {
                    game.onLoose(player);
                }
            }
        }

        game.getArena().getPool().fillWith(new FillAllButOne(), new String[0]);
    }

    @Override
    public void onTurn(GameTurn turn) {
        turn.sendToPlayer(DACMessage.GamePlayerTurn2);
        turn.sendToOthers(DACMessage.GamePlayerTurn);
    }

    @Override
    public void onSuccess(GameJumpSuccess success) {
        success.sendToPlayer(DACMessage.GameJumpSuccess2);
        success.sendToOthers(DACMessage.GameJumpSuccess);
    }

    @Override
    public void onFail(GameJumpFail fail) {
        fail.getPlayer(SuddenDeathGamePlayer.class).setDeadThisTurn();
        fail.sendToPlayer(DACMessage.GameJumpFail2);
        fail.sendToOthers(DACMessage.GameJumpFail);
    }
    
    @Override
    public void onLoose(GameLoose loose) {
    	loose.send(DACMessage.GamePlayerQuit, loose.getPlayer().getDisplayName());
    }

}
