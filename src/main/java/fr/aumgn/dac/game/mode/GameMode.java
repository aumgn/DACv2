package fr.aumgn.dac.game.mode;

import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.options.GameOptions;
import fr.aumgn.dac.joinstage.JoinStage;
import fr.aumgn.dac.player.DACPlayer;

public interface GameMode<T extends DACPlayer> {
	
	Game<T> createGame(JoinStage<?> joinStage, GameOptions options);

	GameModeHandler<T> createHandler(Game<T> game);
	
	T createPlayer(Game<T> game, DACPlayer player, int index);
	
}
