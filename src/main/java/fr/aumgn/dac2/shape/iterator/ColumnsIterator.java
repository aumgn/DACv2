package fr.aumgn.dac2.shape.iterator;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.shape.Column;
import fr.aumgn.dac2.shape.FlatShape;

public class ColumnsIterator extends CuboidColumnsIterator {

    public ColumnsIterator(FlatShape shape) {
        super(shape);
    }

    @Override
    protected Column computeNext() {
        Vector2D pt = computeNextVector2D();
        while (pt != null) {
            if (shape.contains2D(pt)) {
                return new Column(shape, pt);
            }
            pt = computeNextVector2D();
        }

        return endOfData();
    }
}
