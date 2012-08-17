package fr.aumgn.dac2.arena;

import fr.aumgn.bukkitutils.geom.Position;
import fr.aumgn.dac2.regions.DACRegion;

public class Arena {

    private String name;
    private DACRegion pool;
    private DACRegion startRegion;
    private Position diving;

    public Arena(String name) {
        this.name = name;
        pool = null;
        startRegion = null;
        diving = null;
    }

    public String getName() {
        return name;
    }

    public DACRegion getPool() {
        return pool;
    }

    public DACRegion getStartRegion() {
        return startRegion;
    }

    public Position getDiving() {
        return diving;
    }
}
