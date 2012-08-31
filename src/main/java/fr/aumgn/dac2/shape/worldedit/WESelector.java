package fr.aumgn.dac2.shape.worldedit;

import static fr.aumgn.dac2.shape.worldedit.WEShapeUtils.*;

import java.util.ArrayList;
import java.util.List;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.regions.CuboidRegionSelector;
import com.sk89q.worldedit.regions.CylinderRegionSelector;
import com.sk89q.worldedit.regions.EllipsoidRegionSelector;
import com.sk89q.worldedit.regions.ExtendingCuboidRegionSelector;
import com.sk89q.worldedit.regions.Polygonal2DRegionSelector;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.regions.SphereRegionSelector;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.exceptions.InvalidSelectorForRegion;
import fr.aumgn.dac2.exceptions.WESelectionNotSupported;
import fr.aumgn.dac2.shape.CuboidShape;
import fr.aumgn.dac2.shape.CylinderShape;
import fr.aumgn.dac2.shape.EllipsoidShape;
import fr.aumgn.dac2.shape.PolygonalShape;
import fr.aumgn.dac2.shape.Shape;

public enum WESelector {

    Default {
        public RegionSelector create(DAC dac, LocalWorld world,
                Shape shape) {
            if (shape instanceof CuboidShape) {
                return WESelector.Cuboid.create(dac, world, shape);
            } else if (shape instanceof PolygonalShape) {
                return WESelector.Polygonal.create(dac, world, shape);
            } else if (shape instanceof CylinderShape) {
                return WESelector.Cylinder.create(dac, world, shape); 
            } else if (shape instanceof EllipsoidShape) {
                return WESelector.Ellipsoid.create(dac, world, shape);
            } else {
                throw new WESelectionNotSupported(dac, shape.getClass());
            }
        }
    },

    Cuboid {
        public CuboidRegionSelector create(DAC dac, LocalWorld world,
                Shape shape) {
            if (!(shape instanceof CuboidShape)) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }

            CuboidShape cuboid = (CuboidShape) shape;
            return new CuboidRegionSelector(world, bu2we(cuboid.getMin()),
                    bu2we(cuboid.getMax()));
        }
    },

    Extending {
        public ExtendingCuboidRegionSelector create(DAC dac, LocalWorld world,
                Shape shape) {
            if (!(shape instanceof CuboidShape)) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }

            CuboidShape cuboid = (CuboidShape) shape;
            return new ExtendingCuboidRegionSelector(world,
                    bu2we(cuboid.getMin()), bu2we(cuboid.getMax()));
        }
    },

    Polygonal {
        public Polygonal2DRegionSelector create(DAC dac, LocalWorld world,
                Shape shape) {
            if (!(shape instanceof PolygonalShape)) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }

            PolygonalShape poly = (PolygonalShape) shape;
            List<BlockVector2D> wePoints = new ArrayList<BlockVector2D>();
            for (Vector2D pt : poly.getPoints()) {
                wePoints.add(bu2blockwe(pt));
            }
            return new Polygonal2DRegionSelector(world, wePoints,
                    (int) poly.getMinY(), (int) poly.getMaxY());
        }
    },

    Cylinder {
        public CylinderRegionSelector create(DAC dac, LocalWorld world,
                Shape shape) {
            if (!(shape instanceof CylinderShape)) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }

            CylinderShape cyl = (CylinderShape) shape;
            return new CylinderRegionSelector(world, bu2we(cyl.getCenter()),
                    bu2we(cyl.getRadius()), (int) cyl.getMinY(),
                    (int) cyl.getMaxY()); 
        }
    },

    Sphere {
        public SphereRegionSelector create(DAC dac, LocalWorld world,
                Shape shape) {
            if (!(shape instanceof EllipsoidShape)) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }

            EllipsoidShape ellipsoid = (EllipsoidShape) shape;
            Vector radius = ellipsoid.getRadius();
            if (radius.getX() != radius.getY()
                    || radius.getX() != radius.getZ()) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }
            return new SphereRegionSelector(world,
                    bu2we(ellipsoid.getCenter()), radius.getBlockX());
        }
    },

    Ellipsoid {
        public EllipsoidRegionSelector create(DAC dac, LocalWorld world,
                Shape shape) {
            if (!(shape instanceof EllipsoidShape)) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }

            EllipsoidShape ellipsoid = (EllipsoidShape) shape;
            return new EllipsoidRegionSelector(world,
                    bu2we(ellipsoid.getCenter()),
                    bu2we(ellipsoid.getRadius()));
        }
    };

    public abstract RegionSelector create(DAC dac, LocalWorld world,
            Shape shape);
}
