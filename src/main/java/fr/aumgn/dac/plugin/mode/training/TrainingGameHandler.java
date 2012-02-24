package fr.aumgn.dac.plugin.mode.training;

import fr.aumgn.dac.api.area.column.GlassyColumn;
import fr.aumgn.dac.api.area.column.UniformColumn;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.event.GameFinish;
import fr.aumgn.dac.api.game.event.GameJumpFail;
import fr.aumgn.dac.api.game.event.GameJumpSuccess;
import fr.aumgn.dac.api.game.event.GameLoose;
import fr.aumgn.dac.api.game.event.GameStart;
import fr.aumgn.dac.api.game.event.GameTurn;
import fr.aumgn.dac.api.game.mode.SimpleGameHandler;

public class TrainingGameHandler extends SimpleGameHandler {

    @Override
    public void onStart(GameStart start) {
        start.send(DACMessage.GameStart);
    }

    @Override
    public void onTurn(GameTurn turn) {
        turn.sendToPlayer(DACMessage.GamePlayerTurn2);
        turn.sendToOthers(DACMessage.GamePlayerTurn);
    }

    @Override
    public void onSuccess(GameJumpSuccess success) {
    	TrainingGamePlayer player = success.getPlayer(TrainingGamePlayer.class);
        if (success.isADAC()) {
            player.incrementDACs();
            success.setColumnPattern(new GlassyColumn(player.getColor()));
            success.sendToPlayer(DACMessage.GameDAC2);
            success.sendToOthers(DACMessage.GameDAC);
        } else {
            player.incrementSuccesses();
            success.setColumnPattern(new UniformColumn(player.getColor()));
            success.sendToPlayer(DACMessage.GameJumpSuccess2);
            success.sendToOthers(DACMessage.GameJumpSuccess);
        }
    }

    @Override
    public void onFail(GameJumpFail fail) {
    	TrainingGamePlayer player = fail.getPlayer(TrainingGamePlayer.class);
        player.incrementFails();
        fail.sendToPlayer(DACMessage.GameJumpFail2);
        fail.sendToOthers(DACMessage.GameJumpFail);
    }
    
    private void sendStats(TrainingGamePlayer player) {
        player.send(DACMessage.StatsSuccess.format(player.getSuccesses()));
        player.send(DACMessage.StatsDAC.format(player.getDACs()));
        player.send(DACMessage.StatsFail.format(player.getFails()));
    }

    @Override
    public void onLoose(GameLoose loose) {
        sendStats(loose.getPlayer(TrainingGamePlayer.class));
    }

    @Override
    public void onFinish(GameFinish finish) {
        for (TrainingGamePlayer player : finish.getPlayers(TrainingGamePlayer.class)) {
            sendStats(player);
        }
    }

}
