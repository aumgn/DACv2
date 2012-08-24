package fr.aumgn.dac2.game.classic;

import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.dac2.game.GamePlayer;
import fr.aumgn.dac2.stage.JoinPlayerData;

public class ClassicGamePlayer extends GamePlayer {

    public ClassicGamePlayer(PlayerId playerId, JoinPlayerData joinData) {
        super(playerId, joinData);
    }
}
