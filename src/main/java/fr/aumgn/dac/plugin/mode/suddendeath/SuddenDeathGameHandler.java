package fr.aumgn.dac.plugin.mode.suddendeath;

import java.util.List;

import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.fillstrategy.defaults.FillAllButOne;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.mode.SimpleGameHandler;

public class SuddenDeathGameHandler extends SimpleGameHandler<SuddenDeathGamePlayer> {

    private Game<SuddenDeathGamePlayer> game;
    private SuddenDeathGamePlayer lastPlayer;

    public SuddenDeathGameHandler(Game<SuddenDeathGamePlayer> game) {
        this.game = game;
    }

    @Override
    public void onNewTurn() {
        List<SuddenDeathGamePlayer> players = game.getPlayers();
        int playersLeftCount = players.size();
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
        } else if (playersLeftCount == 1) {
            game.send(DACMessage.GameWinner.format(lastPlayer.getDisplayName()));
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
    public void onTurn(SuddenDeathGamePlayer player) {
        player.send(DACMessage.GamePlayerTurn2);
        player.sendToOthers(DACMessage.GamePlayerTurn.format(player.getDisplayName()));
        player.tpToDiving();
    }

    @Override
    public void onSuccess(SuddenDeathGamePlayer player) {
        lastPlayer = player;
        player.send(DACMessage.GameJumpSuccess2);
        player.sendToOthers(DACMessage.GameJumpSuccess.format(player.getDisplayName()));
        player.tpAfterJump();
        game.nextTurn();
    }

    @Override
    public void onFail(SuddenDeathGamePlayer player) {
        player.setDeadThisTurn();
        player.send(DACMessage.GameJumpFail2);
        player.sendToOthers(DACMessage.GameJumpFail.format(player.getDisplayName()));
        player.tpAfterJump();
        game.nextTurn();
    }

}
