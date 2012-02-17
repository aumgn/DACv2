package fr.aumgn.dac.plugin.mode.suddendeath;

import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.fillstrategy.defaults.FillAllButOne;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.mode.SimpleGameModeHandler;

public class SuddenDeathGameModeHandler extends SimpleGameModeHandler<SuddenDeathGamePlayer> {
	
	private Game<SuddenDeathGamePlayer> game;
	private int playersLeftCount;
	private SuddenDeathGamePlayer lastPlayer;

	public SuddenDeathGameModeHandler(Game<SuddenDeathGamePlayer> game) {
		this.game = game;
	}
	
	@Override
	public void onStart() {
		playersLeftCount = game.getPlayers().size();
	}

	@Override
	public void onNewTurn() {
		if (playersLeftCount == 0) {
			for (SuddenDeathGamePlayer dacPlayer : game.getPlayers()) {
				SuddenDeathGamePlayer player = (SuddenDeathGamePlayer)dacPlayer;
				if (player.isDeadThisTurn()) {
					playersLeftCount++;
					player.cancelDeadThisTurn();
				}
			}
		} else if (playersLeftCount == 1) {
			game.send(DACMessage.GameWinner.format(lastPlayer.getDisplayName()));
			game.stop();
			return;
		} else {
			for (SuddenDeathGamePlayer dacPlayer : game.getPlayers()) {
				SuddenDeathGamePlayer player = (SuddenDeathGamePlayer)dacPlayer;
				if (player.isDeadThisTurn()) {
					player.setDead();
				}
			}
		}
		
		game.getArena().getPool().fillWith(new FillAllButOne(), new String[0]);
	}
	
	@Override
	public void onTurn(SuddenDeathGamePlayer player) {
		if (player.isDead()) {
			game.nextTurn();
			return;
		}
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
		playersLeftCount--;
		player.setDeadThisTurn();
		player.send(DACMessage.GameJumpFail2);
		player.sendToOthers(DACMessage.GameJumpFail.format(player.getDisplayName()));
		player.tpAfterJump();
		game.nextTurn();
	}
	
}
