package fr.aumgn.dac.plugin.classicmode;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.SimpleGame;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.game.mode.GameModeHandler;
import fr.aumgn.dac.api.joinstage.JoinStage;
import fr.aumgn.dac.api.stage.StagePlayer;

@DACGameMode(name="classic")
public class ClassicGameMode implements GameMode<ClassicGamePlayer> {

	@Override
	public Game<ClassicGamePlayer> createGame(JoinStage<?> joinStage, GameOptions options) {
		return new SimpleGame<ClassicGamePlayer>(this, joinStage, options);
	}

	@Override
	public GameModeHandler<ClassicGamePlayer> createHandler(Game<ClassicGamePlayer> game) {
		return new ClassicGameModeHandler(game);
	}

	@Override
	public ClassicGamePlayer createPlayer(Game<ClassicGamePlayer> game, StagePlayer player, int index) {
		return new ClassicGamePlayer(game, player, index);
	}

}
