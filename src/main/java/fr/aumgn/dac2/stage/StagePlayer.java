package fr.aumgn.dac2.stage;

import fr.aumgn.bukkitutils.geom.Position;
import fr.aumgn.dac2.config.Color;

import java.util.UUID;

public interface StagePlayer {

    UUID getPlayerID();

    Color getColor();

    Position getStartPosition();

    String getDisplayName();
}
