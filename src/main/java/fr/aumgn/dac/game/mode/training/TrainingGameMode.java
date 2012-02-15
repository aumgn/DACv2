package fr.aumgn.dac.game.mode.training;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.SimpleGame;
import fr.aumgn.dac.game.mode.DACGameMode;
import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.game.options.GameOptions;
import fr.aumgn.dac.joinstage.JoinStage;
import fr.aumgn.dac.player.DACPlayer;

@DACGameMode(name="training", minPlayers=1, allowPoolReset=true)
public class TrainingGameMode implements GameMode<TrainingGamePlayer> {

	@Override
	public Game<TrainingGamePlayer> createGame(JoinStage<?> joinStage, GameOptions options) {
		return new SimpleGame<TrainingGamePlayer>(joinStage, this, options);
	}

	@Override
	public GameModeHandler<TrainingGamePlayer> createHandler(Game<TrainingGamePlayer> game) {
		return new TrainingGameModeHandler(game);
	}

	@Override
	public TrainingGamePlayer createPlayer(Game<TrainingGamePlayer> game, DACPlayer player, int index) {
		return new TrainingGamePlayer(game, player, index);
	}

}
