package fr.aumgn.dac.plugin.area;

import java.util.Iterator;

import org.bukkit.World;
import org.bukkit.block.Block;

import com.sk89q.worldedit.Vector;

public class AreaIterator implements Iterator<Block> {

    private World world;
    private Iterator<? extends Vector> weIterator;

    public AreaIterator(DACArea area, Iterator<? extends Vector> weIterator) {
        this.world = area.getArena().getWorld();
        this.weIterator = weIterator;
    }

    @Override
    public boolean hasNext() {
        return weIterator.hasNext();
    }

    @Override
    public Block next() {
        Vector next = weIterator.next();
        return world.getBlockAt(next.getBlockX(), next.getBlockY(), next.getBlockZ());
    }

    @Override
    public void remove() {
        weIterator.remove();
    }

}