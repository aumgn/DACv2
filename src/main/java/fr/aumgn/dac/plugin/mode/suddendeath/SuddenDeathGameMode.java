package fr.aumgn.dac.plugin.mode.suddendeath;

import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.SimpleGame;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.game.mode.GameModeHandler;
import fr.aumgn.dac.api.joinstage.JoinStage;
import fr.aumgn.dac.api.stage.StagePlayer;

@DACGameMode(name = "sudden-death", isDefault = false)
public class SuddenDeathGameMode implements GameMode<SuddenDeathGamePlayer> {

    @Override
    public Game<SuddenDeathGamePlayer> createGame(JoinStage<?> joinStage, GameOptions options) {
        return new SimpleGame<SuddenDeathGamePlayer>(this, joinStage, options);
    }

    @Override
    public GameModeHandler<SuddenDeathGamePlayer> createHandler(Game<SuddenDeathGamePlayer> game) {
        return new SuddenDeathGameModeHandler(game);
    }

    @Override
    public SuddenDeathGamePlayer createPlayer(Game<SuddenDeathGamePlayer> game, StagePlayer player, int index) {
        return new SuddenDeathGamePlayer(game, player, index);
    }

}
