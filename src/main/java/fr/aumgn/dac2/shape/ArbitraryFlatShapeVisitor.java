package fr.aumgn.dac2.shape;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.exceptions.TooLargeArbitraryShape;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class ArbitraryFlatShapeVisitor {

    private static final int MAXIMUM_ITERATION = 100000;

    private final DAC dac;
    private final World world;
    private final Material material;
    private final byte data;

    private final Set<Vector> visited;
    private final Deque<Vector> queue;

    private final Set<Vector2D> points;
    private int minY;
    private int maxY;
    private int count;

    public ArbitraryFlatShapeVisitor(DAC dac, World world, Vector pos) {
        this.dac = dac;
        this.world = world;
        Block block = pos.toBlock(world);
        this.material = block.getType();
        this.data = block.getData();

        this.visited = new HashSet<Vector>();
        this.queue = new ArrayDeque<Vector>();
        this.points = new HashSet<Vector2D>();

        visited.add(pos);
        pushNeighbors(pos);
        points.add(pos.to2D());
        minY = maxY = pos.getBlockY();

        count = 0;
    }

    public ArbitraryFlatShape visit() {
        while (!queue.isEmpty()) {
            visitQueue();

            count++;
            if (count > MAXIMUM_ITERATION) {
                throw new TooLargeArbitraryShape(dac);
            }
        }

        return new ArbitraryFlatShape(points, minY, maxY);
    }

    private void visitQueue() {
        Vector pos = queue.poll();
        if (visited.contains(pos)) {
            return;
        }

        visited.add(pos);
        Block block = pos.toBlock(world);
        if (block.getType() != material || block.getData() != data) {
            return;
        }

        points.add(pos.to2D());
        int y = pos.getBlockY();
        if (y < minY) {
            minY = y;
        }
        if (y > maxY) {
            maxY = y;
        }

        pushNeighbors(pos);
    }

    private void pushNeighbors(Vector pos) {
        queue.add(pos.subtractX(1));
        queue.add(pos.addX(1));
        queue.add(pos.subtractY(1));
        queue.add(pos.addY(1));
        queue.add(pos.subtractZ(1));
        queue.add(pos.addZ(1));
    }
}
