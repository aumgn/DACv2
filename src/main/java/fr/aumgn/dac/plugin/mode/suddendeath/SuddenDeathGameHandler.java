package fr.aumgn.dac.plugin.mode.suddendeath;

import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.event.GameJumpFail;
import fr.aumgn.dac.api.game.event.GameJumpSuccess;
import fr.aumgn.dac.api.game.event.GameLoose;
import fr.aumgn.dac.api.game.event.GameNewTurn;
import fr.aumgn.dac.api.game.event.GameQuit;
import fr.aumgn.dac.api.game.event.GameTurn;
import fr.aumgn.dac.api.game.mode.SimpleGameHandler;
import fr.aumgn.dac.plugin.fillstrategy.FillAllButOne;

public class SuddenDeathGameHandler extends SimpleGameHandler {


    @Override
    public void onNewTurn(GameNewTurn event) {
        Game game = event.getGame();
        Iterable<SuddenDeathGamePlayer> players = event.getPlayers(SuddenDeathGamePlayer.class);
        int playersLeftCount = game.getPlayers().size();
        for (SuddenDeathGamePlayer player : players) {
            if (player.isDeadThisTurn()) {
                playersLeftCount--;
            }
        }
        if (playersLeftCount == 0) {
            for (SuddenDeathGamePlayer player : players) {
                if (player.isDeadThisTurn()) {
                    player.cancelDeadThisTurn();
                }
            }
        } else {
            for (SuddenDeathGamePlayer player : players) {
                if (player.isDeadThisTurn()) {
                    event.addLoss(player);
                }
            }
        }

        game.getArena().getPool().fillWith(new FillAllButOne(), new String[0]);
    }

    @Override
    public void onTurn(GameTurn event) {
        event.sendToPlayer(DACMessage.GamePlayerTurn2);
        event.sendToOthers(DACMessage.GamePlayerTurn);
    }

    @Override
    public void onSuccess(GameJumpSuccess event) {
        event.setColumnPattern(null);
        event.sendToPlayer(DACMessage.GameJumpSuccess2);
        event.sendToOthers(DACMessage.GameJumpSuccess);
    }

    @Override
    public void onFail(GameJumpFail event) {
        event.getPlayer(SuddenDeathGamePlayer.class).setDeadThisTurn();
        event.sendToPlayer(DACMessage.GameJumpFail2);
        event.sendToOthers(DACMessage.GameJumpFail);
        event.setCancelDeath(true);
    }

    @Override
    public void onLoose(GameLoose event) {
        if (event instanceof GameQuit) {
            event.send(DACMessage.GamePlayerQuit, event.getPlayer().getDisplayName());
        }
        event.send(DACMessage.GamePlayerEliminated, event.getPlayer().getDisplayName());
    }

}
