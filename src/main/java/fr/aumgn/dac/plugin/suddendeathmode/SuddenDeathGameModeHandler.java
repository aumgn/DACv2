package fr.aumgn.dac.plugin.suddendeathmode;

import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.mode.SimpleGameModeHandler;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.plugin.area.filler.AreaAllButOneStrategy;

public class SuddenDeathGameModeHandler extends SimpleGameModeHandler<SuddenDeathGamePlayer> {
	
	private Game<SuddenDeathGamePlayer> game;
	private int playersLeftCount;
	private StagePlayer lastPlayer;

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
			for (StagePlayer dacPlayer : game.getPlayers()) {
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
			for (StagePlayer dacPlayer : game.getPlayers()) {
				SuddenDeathGamePlayer player = (SuddenDeathGamePlayer)dacPlayer;
				if (player.isDeadThisTurn()) {
					player.setDead();
				}
			}
		}
		
		game.getArena().getPool().fillWith(new AreaAllButOneStrategy());
	}
	
	@Override
	public void onTurn(SuddenDeathGamePlayer dacPlayer) {
		SuddenDeathGamePlayer player = (SuddenDeathGamePlayer) dacPlayer;
		if (player.isDead()) {
			game.nextTurn();
			return;
		}
		game.send(DACMessage.GamePlayerTurn.format(dacPlayer.getDisplayName()));
		dacPlayer.tpToDiving();
	}

	@Override
	public void onSuccess(SuddenDeathGamePlayer dacPlayer) {
		lastPlayer = dacPlayer;
		game.send(DACMessage.GameJumpSuccess.format(dacPlayer.getDisplayName()));
		dacPlayer.tpToStart();
		game.nextTurn();
	}

	@Override
	public void onFail(SuddenDeathGamePlayer dacPlayer) {
		SuddenDeathGamePlayer player = (SuddenDeathGamePlayer) dacPlayer;
		playersLeftCount--;
		player.setDeadThisTurn();
		game.send(DACMessage.GameJumpFail.format(dacPlayer.getDisplayName()));
		dacPlayer.tpToStart();
		game.nextTurn();
	}
	
}
