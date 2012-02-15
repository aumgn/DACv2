package fr.aumgn.dac.plugin.mode.suddendeath;

import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.mode.SimpleGameModeHandler;
import fr.aumgn.dac.plugin.area.fillstrategy.FillAllButOneStrategy;

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
		
		game.getArena().getPool().fillWith(new FillAllButOneStrategy(), new String[0]);
	}
	
	@Override
	public void onTurn(SuddenDeathGamePlayer player) {
		if (player.isDead()) {
			game.nextTurn();
			return;
		}
		game.send(DACMessage.GamePlayerTurn.format(player.getDisplayName()));
		player.tpToDiving();
	}

	@Override
	public void onSuccess(SuddenDeathGamePlayer player) {
		lastPlayer = player;
		game.send(DACMessage.GameJumpSuccess.format(player.getDisplayName()));
		player.tpToStart();
		game.nextTurn();
	}

	@Override
	public void onFail(SuddenDeathGamePlayer player) {
		playersLeftCount--;
		player.setDeadThisTurn();
		game.send(DACMessage.GameJumpFail.format(player.getDisplayName()));
		player.tpToStart();
		game.nextTurn();
	}
	
}
