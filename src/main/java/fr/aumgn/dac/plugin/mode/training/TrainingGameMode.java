package fr.aumgn.dac.plugin.mode.training;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.SimpleGame;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.game.mode.GameHandler;
import fr.aumgn.dac.api.joinstage.JoinStage;
import fr.aumgn.dac.api.stage.StagePlayer;

@DACGameMode(name = "training", minPlayers = 1, allowFill = true, aliases = {"tr", "t"})
public class TrainingGameMode implements GameMode<TrainingGamePlayer> {

    @Override
    public Game<TrainingGamePlayer> createGame(JoinStage<?> joinStage, GameOptions options) {
        return new SimpleGame<TrainingGamePlayer>(this, joinStage, options);
    }

    @Override
    public GameHandler<TrainingGamePlayer> createHandler(Game<TrainingGamePlayer> game) {
        return new TrainingGameHandler(game);
    }

    @Override
    public TrainingGamePlayer createPlayer(Game<TrainingGamePlayer> game, StagePlayer player, int index) {
        return new TrainingGamePlayer(game, player, index);
    }

}
