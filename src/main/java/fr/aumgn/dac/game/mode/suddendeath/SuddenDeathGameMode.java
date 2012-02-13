package fr.aumgn.dac.game.mode.suddendeath;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.mode.DACGameMode;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.player.DACPlayer;

@DACGameMode(name="sudden-death", isDefault=false)
public class SuddenDeathGameMode implements GameMode {

	@Override
	public GameModeHandler createHandler(Game game) {
		return new SuddenDeathGameModeHandler(game);
	}

	@Override
	public DACPlayer createPlayer(Game game, DACPlayer player, int index) {
		return new SuddenDeathGamePlayer(game, player, index);
	}

}
