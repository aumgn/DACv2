package fr.aumgn.dac2.shape;

import fr.aumgn.bukkitutils.geom.Vector2D;

public interface FlatShape extends Shape, Iterable<Column> {

    double getMinY();

    double getMaxY();

    Vector2D getMin2D();

    Vector2D getMax2D();

    boolean contains2D(Vector2D pt);
}
