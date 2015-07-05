package fr.aumgn.dac2.shape;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.DAC;
import org.bukkit.World;

public enum ShapeFactory {

    Cuboid {
        @Override
        public Shape create(DAC dac, World world, Vector center, int radius, int height) {
            return new CuboidShape(center.subtract(radius, 0, radius),
                    center.add(radius, height - 1, radius));
        }
    },

    Cylinder {
        @Override
        public Shape create(DAC dac, World world, Vector center, int radius, int height) {
            Vector2D radiusVec = new Vector2D(radius, radius);
            int y = center.getBlockY();
            return new CylinderShape(center.to2D(), radiusVec, y, y + height - 1);
        }
    },

    Sphere {
        @Override
        public Shape create(DAC dac, World world, Vector center, int radius, int height) {
            Vector radiusVec = new Vector(radius, radius, radius);
            return new EllipsoidShape(center, radiusVec);
        }
    },

    Arbitrary {
        @Override
        public Shape create(DAC dac, World world, Vector center, int radius, int height) {
            ArbitraryFlatShapeVisitor visitor = new ArbitraryFlatShapeVisitor(dac, world, center);
            return visitor.visit();
        }
    };

    public abstract Shape create(DAC dac, World world, Vector center, int radius, int height);
}
