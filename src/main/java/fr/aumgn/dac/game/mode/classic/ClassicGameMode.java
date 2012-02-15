package fr.aumgn.dac.game.mode.classic;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.SimpleGame;
import fr.aumgn.dac.game.mode.DACGameMode;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.game.options.GameOptions;
import fr.aumgn.dac.joinstage.JoinStage;
import fr.aumgn.dac.player.DACPlayer;

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
	public ClassicGamePlayer createPlayer(Game<ClassicGamePlayer> game, DACPlayer player, int index) {
		return new ClassicGamePlayer(game, player, index);
	}

}
