package fr.aumgn.dac2.stage;

import fr.aumgn.bukkitutils.geom.Position;
import fr.aumgn.dac2.config.Color;
import org.bukkit.entity.Player;

import java.util.UUID;

import static fr.aumgn.dac2.utils.DACUtil.playerDisplayName;

public abstract class SimpleStagePlayer implements StagePlayer {

    private final UUID playerID;
    private final Color color;
    private final Position position;

    protected SimpleStagePlayer(Color color, Player player) {
        this.playerID = player.getUniqueId();
        this.color = color;
        this.position = new Position(player.getLocation());
    }

    protected SimpleStagePlayer(StagePlayer player) {
        this.playerID = player.getPlayerID();
        this.color = player.getColor();
        this.position = player.getStartPosition();
    }

    @Override
    public UUID getPlayerID() {
        return playerID;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Position getStartPosition() {
        return position;
    }

    @Override
    public String getDisplayName() {
        return playerDisplayName(playerID);
    }
}
