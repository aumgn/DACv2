package fr.aumgn.dac2.shape;

import static fr.aumgn.dac2.utils.WEUtils.*;

import java.util.Iterator;

import org.bukkit.World;

import com.sk89q.worldedit.regions.CylinderRegion;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.shape.iterator.ColumnsIterator;
import fr.aumgn.dac2.utils.CylinderSelection;

@ShapeName("cylinder")
public class CylinderShape implements FlatShape {

    private final Vector2D center;
    private final Vector2D radius;
    private final int minY;
    private final int maxY;

    public CylinderShape(CylinderRegion region) {
        this.center = we2bu(region.getCenter().toVector2D());
        this.radius = we2bu(region.getRadius()).add(0.5);
        this.minY = region.getMinimumY();
        this.maxY = region.getMaximumY();
    }

    @Override
    public boolean contains(Vector pt) {
        return pt.getY() >= minY && pt.getY() <= maxY
                && pt.to2D().subtract(center).divide(radius).lengthSq() <= 1;
    }

    @Override
    public CylinderSelection getSelection(World world) {
        return new CylinderSelection(world, bu2we(center),
                bu2we(radius.subtract(0.5)), minY, maxY);
    }

    @Override
    public double getMinY() {
        return minY;
    }

    @Override
    public double getMaxY() {
        return maxY;
    }

    @Override
    public Vector2D getMin2D() {
        return center.subtract(radius.subtract(0.5));
    }

    @Override
    public Vector2D getMax2D() {
        return center.add(radius.subtract(0.5));
    }

    @Override
    public boolean contains2D(Vector2D pt) {
        return pt.subtract(center).divide(radius).lengthSq() <= 1;
    }

    @Override
    public Column getColumn(Vector2D pt) {
        return new Column(this, pt);
    }

    @Override
    public Iterator<Column> iterator() {
        return new ColumnsIterator(this);
    }
}
