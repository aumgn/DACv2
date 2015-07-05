package fr.aumgn.dac2.game.colonnisation;

import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.shape.FlatShape;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class PoolVisitor {

    private final Color color;
    private final World world;
    private final FlatShape shape;
    private final int y;
    private final Set<Vector2D> visited;
    private final Deque<Vector2D> queue;

    private int count;

    public PoolVisitor(Arena arena, Color color) {
        this(arena.getWorld(), arena.getPool(), color);
    }

    public PoolVisitor(World world, Pool pool, Color color) {
        this.color = color;
        this.world = world;
        this.shape = pool.getShape();
        this.y = (int) shape.getMaxY() - 1;
        this.visited = new HashSet<Vector2D>();
        this.queue = new ArrayDeque<Vector2D>();

    }

    public int visit(Vector2D pos) {
        visited.clear();
        queue.clear();
        count = 0;

        visited.add(pos);
        pushNeighbors(pos);
        while (!queue.isEmpty()) {
            visit();
        }

        return count + 1;
    }

    private boolean isValid(Vector2D pos) {
        return shape.contains2D(pos) && isValidBlock(pos);
    }

    private boolean isValidBlock(Vector2D pos) {
        Block block = pos.to3D(y).toBlock(world);
        return block.getType() == color.block && block.getData() == color.data;
    }

    private void visit() {
        Vector2D pos = queue.poll();
        if (visited.contains(pos)) {
            return;
        }

        visited.add(pos);
        if (!isValid(pos)) {
            return;
        }

        count++;
        pushNeighbors(pos);
    }

    private void pushNeighbors(Vector2D pos) {
        queue.add(pos.subtractX(1));
        queue.add(pos.addX(1));
        queue.add(pos.subtractZ(1));
        queue.add(pos.addZ(1));
    }
}
