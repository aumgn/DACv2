package fr.aumgn.dac.plugin.mode.training;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.TurnBasedGame;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.game.mode.GameHandler;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;

@DACGameMode(name = "training", minPlayers = 1, allowFill = true, aliases = {"tr", "t"})
public class TrainingGameMode implements GameMode {

    @Override
    public Game createGame(Stage stage, GameOptions options) {
        return new TurnBasedGame(this, stage, options);
    }

    @Override
    public GameHandler createHandler() {
        return new TrainingGameHandler();
    }

    @Override
    public TrainingGamePlayer createPlayer(Game game, StagePlayer player, int index) {
        return new TrainingGamePlayer(game, player, index);
    }

}
