package fr.aumgn.dac.api.game;

import fr.aumgn.dac.api.stage.SimpleStagePlayer;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.util.GamePlayerTeleporter;

public class SimpleGamePlayer extends SimpleStagePlayer {

    private int index;

    public SimpleGamePlayer(Game game, StagePlayer player, int index) {
        super(player.getPlayer(), game, player.getColor(), player.getStartLocation());
        this.index = index;
    }

    @Override
    public String formatForList() {
        return " " + index + super.formatForList();
    }

    public int getIndex() {
        return index;
    }

    @Override
    public GamePlayerTeleporter teleporter() {
        return new GamePlayerTeleporter(this);
    }

}
