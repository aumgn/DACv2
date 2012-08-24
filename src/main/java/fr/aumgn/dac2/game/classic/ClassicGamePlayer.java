package fr.aumgn.dac2.game.classic;

import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.dac2.game.GamePlayer;
import fr.aumgn.dac2.stage.JoinPlayerData;

public class ClassicGamePlayer extends GamePlayer {

    private int index;

    public ClassicGamePlayer(PlayerId playerId, JoinPlayerData joinData,
            int index) {
        super(playerId, joinData);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
