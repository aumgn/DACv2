package fr.aumgn.dac2.game.start;

import fr.aumgn.bukkitutils.playerref.map.PlayersRefMap;
import fr.aumgn.bukkitutils.playerref.set.PlayersRefSet;
import fr.aumgn.dac2.arena.Arena;

/**
 * Defines methods for all data necessary to start a new Game.
 * (e.g. arena, players and their respective data, spectators)
 */
public interface GameStartData {

    Arena getArena();

    PlayersRefMap<? extends PlayerStartData> getPlayersData();

    PlayersRefSet getSpectators();
}
