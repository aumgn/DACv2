package fr.aumgn.dac.api.arena;

import org.bukkit.Location;

public interface DivingBoard {

    Location getLocation();

    void update(Location location);

}
