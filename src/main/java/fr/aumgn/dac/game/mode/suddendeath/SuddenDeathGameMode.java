package fr.aumgn.dac.game.mode.suddendeath;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.SimpleGame;
import fr.aumgn.dac.game.mode.DACGameMode;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.game.options.GameOptions;
import fr.aumgn.dac.joinstage.JoinStage;
import fr.aumgn.dac.stage.StagePlayer;

@DACGameMode(name="sudden-death", isDefault=false)
public class SuddenDeathGameMode implements GameMode<SuddenDeathGamePlayer> {

	@Override
	public Game<SuddenDeathGamePlayer> createGame(JoinStage<?> joinStage, GameOptions options) {
		return new SimpleGame<SuddenDeathGamePlayer>(this, joinStage, options);
	}

	@Override
	public GameModeHandler<SuddenDeathGamePlayer> createHandler(Game<SuddenDeathGamePlayer> game) {
		return new SuddenDeathGameModeHandler(game);
	}

	@Override
	public SuddenDeathGamePlayer createPlayer(Game<SuddenDeathGamePlayer> game, StagePlayer player, int index) {
		return new SuddenDeathGamePlayer(game, player, index);
	}

}
