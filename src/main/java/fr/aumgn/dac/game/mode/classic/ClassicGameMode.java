package fr.aumgn.dac.game.mode.classic;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.mode.DACGameMode;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.player.DACPlayer;

@DACGameMode(name="classic")
public class ClassicGameMode implements GameMode {

	@Override
	public GameModeHandler createHandler(Game game) {
		return new ClassicGameModeHandler(game);
	}

	@Override
	public DACPlayer createPlayer(Game game, DACPlayer player, int index) {
		return new ClassicGamePlayer(game, player, index);
	}

}
