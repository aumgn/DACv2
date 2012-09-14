package fr.aumgn.dac2.game.start;

import java.util.UUID;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.config.Color;

/**
 * Defines methods for all data associated to a player to start a new Game.
 */
public interface PlayerStartData {

    Color getColor();

    UUID getWorldId();

    Vector getPosition();

    Direction getDirection();
}
