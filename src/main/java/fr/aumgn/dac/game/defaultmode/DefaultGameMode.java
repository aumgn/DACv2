package fr.aumgn.dac.game.defaultmode;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.mode.DACGameMode;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.player.DACPlayer;

@DACGameMode("default")
public class DefaultGameMode implements GameMode {

	@Override
	public GameModeHandler createHandler(Game game) {
		return new DefaultGameModeHandler(game);
	}

	@Override
	public DACPlayer createPlayer(Game game, DACPlayer player) {
		return new DefaultGamePlayer(game, player);
	}

	@Override
	public int getMinimumPlayer() {
		return 2;
	}

}
