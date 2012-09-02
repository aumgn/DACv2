package fr.aumgn.dac2.game.start;

import java.util.Map;
import java.util.Set;
import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.aumgn.dac2.arena.Arena;

/**
 * Defines methods for all data necessary to start a new Game.
 * (e.g. arena, players and their respective data, spectators)
 */
public interface GameStartData {

    Arena getArena();

    Map<PlayerRef, ? extends PlayerStartData> getPlayersData();

    Set<PlayerRef> getSpectators();
}
