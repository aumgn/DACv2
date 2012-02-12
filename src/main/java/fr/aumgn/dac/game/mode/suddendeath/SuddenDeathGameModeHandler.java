package fr.aumgn.dac.game.mode.suddendeath;

import fr.aumgn.dac.area.filler.AreaAllButOneFiller;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.mode.SimpleGameModeHandler;
import fr.aumgn.dac.player.DACPlayer;

public class SuddenDeathGameModeHandler extends SimpleGameModeHandler {
	
	private Game game;
	private int playersLeftCount;
	private DACPlayer lastPlayer;

	public SuddenDeathGameModeHandler(Game game) {
		this.game = game;
	}
	
	@Override
	public void onStart() {
		playersLeftCount = game.getPlayers().size();
	}

	@Override
	public void onNewTurn() {
		if (playersLeftCount == 0) {
			for (DACPlayer dacPlayer : game.getPlayers()) {
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
			for (DACPlayer dacPlayer : game.getPlayers()) {
				SuddenDeathGamePlayer player = (SuddenDeathGamePlayer)dacPlayer;
				if (player.isDeadThisTurn()) {
					player.setDead();
				}
			}
		}
		
		game.getArena().getPool().fillWith(new AreaAllButOneFiller());
	}
	
	@Override
	public void onTurn(DACPlayer dacPlayer) {
		SuddenDeathGamePlayer player = (SuddenDeathGamePlayer) dacPlayer;
		if (player.isDead()) {
			game.nextTurn();
		}
		game.send(DACMessage.GamePlayerTurn.format(dacPlayer.getDisplayName()));
		dacPlayer.tpToDiving();
	}

	@Override
	public void onSuccess(DACPlayer dacPlayer) {
		lastPlayer = dacPlayer;
		game.send(DACMessage.GameJumpSuccess.format(dacPlayer.getDisplayName()));
		dacPlayer.tpToStart();
		game.nextTurn();
	}

	@Override
	public void onFail(DACPlayer dacPlayer) {
		SuddenDeathGamePlayer player = (SuddenDeathGamePlayer) dacPlayer;
		playersLeftCount--;
		player.setDeadThisTurn();
		game.send(DACMessage.GameJumpFail.format(dacPlayer.getDisplayName()));
		dacPlayer.tpToStart();
		game.nextTurn();
	}
	
}
