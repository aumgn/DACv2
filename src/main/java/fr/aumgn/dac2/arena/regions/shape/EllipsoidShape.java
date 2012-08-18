package fr.aumgn.dac2.arena.regions.shape;

import static fr.aumgn.dac2.utils.WEUtils.*;

import com.sk89q.worldedit.regions.EllipsoidRegion;

import fr.aumgn.bukkitutils.geom.Vector;

@ShapeName("ellipsoid")
public class EllipsoidShape implements Shape {

    private final Vector center;
    private final Vector radius;

    public EllipsoidShape(EllipsoidRegion region) {
        this.center = we2bu(region.getCenter());
        this.radius = we2bu(region.getRadius());
    }

    @Override
    public boolean contains(Vector pt) {
        return pt.subtract(center).divide(radius).lengthSq() <= 1;
    }
}
