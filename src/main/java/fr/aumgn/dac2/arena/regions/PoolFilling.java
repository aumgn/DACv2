package fr.aumgn.dac2.arena.regions;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.bukkitutils.util.Random;
import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.shape.FlatShape;
import fr.aumgn.dac2.shape.column.Column;
import fr.aumgn.dac2.shape.column.ColumnPattern;
import org.bukkit.World;

public interface PoolFilling {

    void fill(World world, Pool pool, ColumnPattern pattern);

    /**
     * Resets the pool.
     */
    class Reset implements PoolFilling {

        @Override
        public void fill(World world, Pool pool, ColumnPattern pattern) {
            for (Column column : pool.getShape()) {
                column.reset(world);
            }
        }
    }

    /**
     * Replaces all columns.
     */
    class Fully implements PoolFilling {

        @Override
        public void fill(World world, Pool pool, ColumnPattern pattern) {
            for (Column column : pool.getShape()) {
                column.set(world, pattern);
            }
        }
    }

    /**
     * Replaces some columns randomly.
     */
    class Randomly implements PoolFilling {

        private final double ratio;

        public Randomly(double ratio) {
            this.ratio = ratio;
        }

        @Override
        public void fill(World world, Pool pool, ColumnPattern pattern) {
            Random rand = Util.getRandom();
            for (Column column : pool.getShape()) {
                if (rand.nextDouble() < ratio) {
                    column.set(world, pattern);
                }
                else {
                    column.reset(world);
                }
            }
        }
    }

    /**
     * Replaces one column over two.
     */
    class DeACoudre implements PoolFilling {

        @Override
        public void fill(World world, Pool pool, ColumnPattern pattern) {
            boolean sameParity = Util.getRandom().nextBoolean();

            for (Column column : pool.getShape()) {
                Vector2D pt = column.getPos();
                if (((pt.getBlockX() & 1) == (pt.getBlockZ() & 1))
                        == sameParity) {
                    column.reset(world);
                }
                else {
                    column.set(world, pattern);
                }
            }
        }
    }

    /**
     * Replaces all columns (except a random one).
     */
    class AllButOne implements PoolFilling {

        @Override
        public void fill(World world, Pool pool, ColumnPattern pattern) {
            Random rand = Util.getRandom();
            FlatShape shape = pool.getShape();

            Vector2D except;
            int minX = shape.getMin2D().getBlockX();
            int minZ = shape.getMin2D().getBlockZ();
            int maxX = shape.getMax2D().getBlockX();
            int maxZ = shape.getMax2D().getBlockZ();
            do {
                except = new Vector2D(rand.nextInt(minX, maxX),
                        rand.nextInt(minZ, maxZ));
            } while (!shape.contains2D(except));

            for (Column column : shape) {
                if (column.getPos().equals(except)) {
                    column.reset(world);
                }
                else {
                    column.set(world, pattern);
                }
            }
        }
    }
}
