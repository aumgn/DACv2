package fr.aumgn.dac2.game;

import fr.aumgn.dac2.stage.Stage;

public interface Game extends Stage {

    /**
     * Callback called when a new turn starts.
     * (ie. all players have jumped once since the last one)
     */
    void onNewTurn();
}
