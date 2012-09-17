package fr.aumgn.dac.plugin.area;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.AreaColumnBlock;
import fr.aumgn.dac.api.area.ColumnPattern;
import fr.aumgn.dac.api.area.VerticalArea;

public class DACAreaColumn implements AreaColumn {

    private class DACColumnIterator implements Iterator<AreaColumnBlock> {

        private int i, y;
        private World world;

        public DACColumnIterator() {
            this.i = 0;
            this.y = bottom;
            this.world = area.getArena().getWorld();
        }

        @Override
        public boolean hasNext() {
            return y <= top;
        }

        @Override
        public AreaColumnBlock next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Block block = world.getBlockAt(x, y, z);
            y++;
            return new DACAreaColumnBlock(i++, block);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private VerticalArea area;
    private int bottom;
    private int top;
    private int x;
    private int z;

    public DACAreaColumn(VerticalArea area, int x, int z) {
        this.area = area;
        this.bottom = area.getBottom();
        this.top = area.getTop();
        this.x = x;
        this.z = z;
    }

    @Override
    public VerticalArea getArea() {
        return area;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public int getBottom() {
        return bottom;
    }

    @Override
    public int getTop() {
        return top;
    }

    @Override
    public Iterator<AreaColumnBlock> iterator() {
        return new DACColumnIterator();
    }

    @Override
    public int getHeight() {
        return top - bottom + 1;
    }

    @Override
    public AreaColumnBlock get(int index) {
        int i = index % getHeight();
        if (i < 0) {
            i += getHeight();
        }
        return new DACAreaColumnBlock(i, area.getArena().getWorld().getBlockAt(x, bottom + i, z));
    }

    @Override
    public void set(ColumnPattern pattern) {
        pattern.set(this);
    }

    @Override
    public void set(Material material) {
        for (AreaColumnBlock block : this) {
            block.setType(material);
        }
    }

    @Override
    public void set(Material material, byte data) {
        for (AreaColumnBlock block : this) {
            block.setType(material, data);
        }
    }

    @Override
    public boolean isWater() {
        Material type = get(-1).getType();
        return type.equals(Material.WATER) || type.equals(Material.STATIONARY_WATER);
    }

}
