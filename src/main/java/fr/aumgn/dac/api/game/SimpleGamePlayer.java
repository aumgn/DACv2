package fr.aumgn.dac.api.game;

import fr.aumgn.dac.api.stage.SimpleStagePlayer;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.util.GamePlayerTeleporter;

public class SimpleGamePlayer extends SimpleStagePlayer {

    public SimpleGamePlayer(Game game, StagePlayer player) {
        super(player.getPlayer(), game, player.getColor(), player.getStartLocation());
    }

    @Override
    public GamePlayerTeleporter teleporter() {
        return new GamePlayerTeleporter(this);
    }

}
