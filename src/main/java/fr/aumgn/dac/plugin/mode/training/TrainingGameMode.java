package fr.aumgn.dac.plugin.mode.training;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.SimpleGame;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.game.mode.GameModeHandler;
import fr.aumgn.dac.api.joinstage.JoinStage;
import fr.aumgn.dac.api.stage.StagePlayer;

@DACGameMode(name = "training", minPlayers = 1, allowFill = true)
public class TrainingGameMode implements GameMode<TrainingGamePlayer> {

    @Override
    public Game<TrainingGamePlayer> createGame(JoinStage<?> joinStage, GameOptions options) {
        return new SimpleGame<TrainingGamePlayer>(this, joinStage, options);
    }

    @Override
    public GameModeHandler<TrainingGamePlayer> createHandler(Game<TrainingGamePlayer> game) {
        return new TrainingGameModeHandler(game);
    }

    @Override
    public TrainingGamePlayer createPlayer(Game<TrainingGamePlayer> game, StagePlayer player, int index) {
        return new TrainingGamePlayer(game, player, index);
    }

}
