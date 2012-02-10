package fr.aumgn.dac.game.mode.training;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.mode.DACGameMode;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.player.DACSimplePlayer;

@DACGameMode(name="training", minPlayers=1, allowPoolReset=true)
public class TrainingGameMode implements GameMode {

	@Override
	public GameModeHandler createHandler(Game game) {
		return new TrainingGameModeHandler(game);
	}

	@Override
	public DACPlayer createPlayer(Game game, DACPlayer player, int index) {
		return new DACSimplePlayer(player, game);
	}

}
