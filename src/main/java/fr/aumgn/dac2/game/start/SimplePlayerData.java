package fr.aumgn.dac2.game.start;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Directions;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.game.start.GameStartData.PlayerData;

public class SimplePlayerData implements PlayerData {

    private final Color color;
    private final UUID worldId;
    private final Vector position;
    private final Direction direction;

    public SimplePlayerData(Color color, Player player) {
        this.color = color;
        Location location = player.getLocation();
        this.worldId = location.getWorld().getUID();
        this.position = new Vector(location);
        this.direction = Directions.fromLocation(location);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public UUID getWorldId() {
        return worldId;
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }
}
