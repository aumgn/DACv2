package fr.aumgn.dac2.stage;

import fr.aumgn.bukkitutils.geom.Position;
import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.aumgn.dac2.config.Color;

public interface StagePlayer {

    PlayerRef getRef();

    Color getColor();

    Position getStartPosition();

    String getDisplayName();
}
