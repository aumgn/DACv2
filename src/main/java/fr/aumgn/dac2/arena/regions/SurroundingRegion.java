package fr.aumgn.dac2.arena.regions;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.arena.Diving;
import fr.aumgn.dac2.shape.CuboidShape;

public class SurroundingRegion extends Region {

    private static final double MARGIN = 5.0;
    private static final double MAX_Y_OFFSET = 3.0;

    private final CuboidShape shape;

    public SurroundingRegion(Diving diving, Pool pool) {
        Vector2D divingPos = diving.getPosition().to2D();
        Vector2D poolMin = pool.getShape().getMin2D();
        Vector2D poolMax = pool.getShape().getMax2D();

        Vector min = new Vector(
                Math.min(poolMin.getX(), divingPos.getX()) - MARGIN,
                pool.getShape().getMinY(),
                Math.min(poolMin.getZ(), divingPos.getZ()) - MARGIN);
        Vector max = new Vector(
                Math.max(poolMax.getX(), divingPos.getX()) + MARGIN,
                diving.getPosition().getY() - MAX_Y_OFFSET,
                Math.max(poolMax.getZ(), divingPos.getZ()) + MARGIN);

        this.shape = new CuboidShape(min, max);
    }

    @Override
    public CuboidShape getShape() {
        return shape;
    }
}
