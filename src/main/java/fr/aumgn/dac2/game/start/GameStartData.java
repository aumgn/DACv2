package fr.aumgn.dac2.game.start;

import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.stage.Spectators;
import fr.aumgn.dac2.stage.StagePlayer;

import java.util.Set;

/**
 * Defines methods for all data necessary to start a new Game.
 * (e.g. arena, players and their respective data, spectators)
 */
public interface GameStartData {

    Arena getArena();

    Set<? extends StagePlayer> getPlayers();

    Spectators getSpectators();
}
