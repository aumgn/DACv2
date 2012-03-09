package fr.aumgn.dac.api.stage;

import java.util.List;

import fr.aumgn.dac.api.arena.Arena;

/**
 * Represents a running stage.
 */
public interface Stage {

    Arena getArena();

    void removePlayer(StagePlayer player, StageQuitReason reason);

    List<StagePlayer> getPlayers();

    void registerAll();

    void unregisterAll();

    void send(Object message);

    void stop();

}
