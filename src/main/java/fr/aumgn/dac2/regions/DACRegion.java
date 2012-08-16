package fr.aumgn.dac2.regions;

import fr.aumgn.bukkitutils.geom.Vector;

public interface DACRegion {

    String getType();

    boolean isWERegion();

    boolean contains(Vector vector);
}
