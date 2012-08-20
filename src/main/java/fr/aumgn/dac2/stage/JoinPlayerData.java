package fr.aumgn.dac2.stage;

import java.util.UUID;

import org.bukkit.Location;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Directions;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.config.Color;

public class JoinPlayerData {

    public final Color color;
    public final UUID worldId;
    public final Vector pos;
    public final Direction dir;

    public JoinPlayerData(Color color, Location location) {
        this.color = color;
        this.worldId = location.getWorld().getUID();
        this.pos = new Vector(location);
        this.dir = Directions.fromLocation(location);
    }
}
