package fr.aumgn.dac2.game.start;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.config.Color;

/**
 * Holds all datas necessary to start a new Game.
 * (e.g. arena, players and their respective data, spectators)
 */
public interface GameStartData {

    public interface PlayerData {

        Color getColor();

        UUID getWorldId();

        Vector getPosition();

        Direction getDirection();
    }

    Arena getArena();

    Map<PlayerId, ? extends PlayerData> getPlayersData();

    Set<PlayerId> getSpectators();
}
