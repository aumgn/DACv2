package fr.aumgn.dac2.shape;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.shape.column.Column;

public interface FlatShape extends Shape, Iterable<Column> {

    double getMinY();

    double getMaxY();

    Vector2D getMin2D();

    Vector2D getMax2D();

    boolean contains2D(Vector2D pt);

    Column getColumn(Vector2D pt);
}
