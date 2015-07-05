package fr.aumgn.dac2.shape.worldedit;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.regions.selector.*;
import com.sk89q.worldedit.world.World;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.exceptions.InvalidSelectorForRegion;
import fr.aumgn.dac2.exceptions.WESelectionNotSupported;
import fr.aumgn.dac2.shape.*;

import java.util.ArrayList;
import java.util.List;

import static fr.aumgn.dac2.shape.worldedit.WEShapeUtils.bukkit2blockWorldedit;
import static fr.aumgn.dac2.shape.worldedit.WEShapeUtils.bukkit2worldedit;

public enum WESelector {

    Default {
        public RegionSelector create(DAC dac, World world,
                                     Shape shape) {
            if (shape instanceof CuboidShape) {
                return WESelector.Cuboid.create(dac, world, shape);
            }
            else if (shape instanceof PolygonalShape) {
                return WESelector.Polygonal.create(dac, world, shape);
            }
            else if (shape instanceof CylinderShape) {
                return WESelector.Cylinder.create(dac, world, shape);
            }
            else if (shape instanceof EllipsoidShape) {
                return WESelector.Ellipsoid.create(dac, world, shape);
            }
            else {
                throw new WESelectionNotSupported(dac, shape.getClass());
            }
        }
    },

    Cuboid {
        public com.sk89q.worldedit.regions.selector.CuboidRegionSelector create(DAC dac, World world,
                                           Shape shape) {
            if (!(shape instanceof CuboidShape)) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }

            CuboidShape cuboid = (CuboidShape) shape;
            return new com.sk89q.worldedit.regions.selector.CuboidRegionSelector(world, bukkit2worldedit(cuboid.getMin()),
                    bukkit2worldedit(cuboid.getMax()));
        }
    },

    Extending {
        public ExtendingCuboidRegionSelector create(DAC dac, World world,
                                                    Shape shape) {
            if (!(shape instanceof CuboidShape)) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }

            CuboidShape cuboid = (CuboidShape) shape;
            return new ExtendingCuboidRegionSelector(world,
                    bukkit2worldedit(cuboid.getMin()), bukkit2worldedit(cuboid.getMax()));
        }
    },

    Polygonal {
        public Polygonal2DRegionSelector create(DAC dac, World world,
                                                Shape shape) {
            if (!(shape instanceof PolygonalShape)) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }

            PolygonalShape poly = (PolygonalShape) shape;
            List<BlockVector2D> wePoints = new ArrayList<BlockVector2D>();
            for (Vector2D pt : poly.getPoints()) {
                wePoints.add(bukkit2blockWorldedit(pt));
            }
            return new Polygonal2DRegionSelector(world, wePoints,
                    (int) poly.getMinY(), (int) poly.getMaxY());
        }
    },

    Cylinder {
        public CylinderRegionSelector create(DAC dac, World world,
                                             Shape shape) {
            if (!(shape instanceof CylinderShape)) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }

            CylinderShape cyl = (CylinderShape) shape;
            return new com.sk89q.worldedit.regions.selector.CylinderRegionSelector(world,
                    bukkit2worldedit(cyl.getCenter()),
                    bukkit2worldedit(cyl.getRadius()),
                    (int) cyl.getMinY(),
                    (int) cyl.getMaxY());
        }
    },

    Sphere {
        public SphereRegionSelector create(DAC dac, World world,
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
                    bukkit2worldedit(ellipsoid.getCenter()), radius.getBlockX());
        }
    },

    Ellipsoid {
        public EllipsoidRegionSelector create(DAC dac, World world,
                                              Shape shape) {
            if (!(shape instanceof EllipsoidShape)) {
                throw new InvalidSelectorForRegion(dac, this, shape.getClass());
            }

            EllipsoidShape ellipsoid = (EllipsoidShape) shape;
            return new EllipsoidRegionSelector(world,
                    bukkit2worldedit(ellipsoid.getCenter()),
                    bukkit2worldedit(ellipsoid.getRadius()));
        }
    };

    public abstract RegionSelector create(DAC dac, World world,
                                          Shape shape);
}
