package fr.aumgn.dac2.shape.iterator;

import org.apache.commons.lang.Validate;

import com.google.common.collect.AbstractIterator;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.shape.Column;
import fr.aumgn.dac2.shape.FlatShape;

public class CuboidColumnsIterator extends AbstractIterator<Column> {

    protected final FlatShape shape;
    private final double minZ;
    private final double maxX;
    private final double maxZ;

    private double x;
    private double z;

    public CuboidColumnsIterator(FlatShape shape) {
        Vector2D min = shape.getMin2D();
        Vector2D max = shape.getMax2D();
        Validate.isTrue(min.getX() <= max.getX());
        Validate.isTrue(min.getZ() <= max.getZ());

        this.shape = shape;
        x = min.getX();
        z = minZ = min.getZ();
        maxX = max.getX();
        maxZ = max.getZ();
    }

    @Override
    protected Column computeNext() {
        Vector2D pt = computeNextVector2D();
        return pt == null ? endOfData() : new Column(shape, pt);
    }

    protected Vector2D computeNextVector2D() {
        if (z >= maxZ) {
            if (x >= maxX) {
                return null;
            } else {
                z = minZ;
                x++;
            }
        } else {
            z++;
        }

        return new Vector2D(x, z);
    }
}
