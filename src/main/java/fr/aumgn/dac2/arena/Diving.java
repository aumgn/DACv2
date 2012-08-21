package fr.aumgn.dac2.arena;

import org.bukkit.Location;
import org.bukkit.World;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Directions;
import fr.aumgn.bukkitutils.geom.Vector;

public class Diving {

    private final Vector position;
    private final Direction direction;

    public Diving(Location location) {
        this.position = new Vector(location);
        this.direction = Directions.fromLocation(location);
    }

    public Vector getPosition() {
        return position;
    }

    public Location toLocation(World world) {
        return position.toLocation(world, direction);
    }
}
