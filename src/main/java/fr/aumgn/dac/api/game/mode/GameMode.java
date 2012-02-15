package fr.aumgn.dac.api.game.mode;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.joinstage.JoinStage;
import fr.aumgn.dac.api.stage.StagePlayer;

public interface GameMode<T extends StagePlayer> {
	
	Game<T> createGame(JoinStage<?> joinStage, GameOptions options);

	GameModeHandler<T> createHandler(Game<T> game);
	
	T createPlayer(Game<T> game, StagePlayer player, int index);
	
}
