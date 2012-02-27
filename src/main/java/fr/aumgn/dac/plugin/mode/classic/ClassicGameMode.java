package fr.aumgn.dac.plugin.mode.classic;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.TurnBasedGame;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;

@DACGameMode(name = "classic", aliases = {"default", "def"})
public class ClassicGameMode implements GameMode {

    @Override
    public Game createGame(Stage stage, GameOptions options) {
        options.parseLives();
        return new TurnBasedGame(stage, this, new ClassicGameHandler(), options);
    }

    @Override
    public ClassicGamePlayer createPlayer(Game game, StagePlayer player, int index) {
        return new ClassicGamePlayer(game, player, index);
    }

}
