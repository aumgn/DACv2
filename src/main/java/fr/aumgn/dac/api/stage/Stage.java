package fr.aumgn.dac.api.stage;

import java.util.List;

import fr.aumgn.dac.api.arena.Arena;

public interface Stage<T extends StagePlayer> {

    Arena getArena();

    void removePlayer(StagePlayer player);

    List<T> getPlayers();

    void registerAll();

    void unregisterAll();

    void send(Object message);

    void send(Object message, StagePlayer exclude);

    void stop();

}
