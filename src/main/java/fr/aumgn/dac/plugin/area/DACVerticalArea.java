package fr.aumgn.dac.plugin.area;

import java.util.Iterator;

import com.sk89q.worldedit.BlockVector2D;

import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.VerticalArea;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;
import fr.aumgn.dac.plugin.arena.DACArena;

public class DACVerticalArea extends DACArea implements VerticalArea {

    public DACVerticalArea(DACArena arena) {
        super(arena);
    }

    @Override
    public int getBottom() {
        return getWERegion().getMinimumPoint().getBlockY();
    }

    @Override
    public int getTop() {
        return getWERegion().getMaximumPoint().getBlockY();
    }

    @Override
    public AreaColumn getColumn(BlockVector2D vec) {
        return new DACAreaColumn(this, vec.getBlockX(), vec.getBlockZ());
    }

    @Override
    public AreaColumn getColumn(int x, int z) {
        return new DACAreaColumn(this, x, z);
    }

    @Override
    public boolean isFull() {
        for (AreaColumn column : columns()) {
            if (column.isWater()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void fillWith(FillStrategy filler, String[] args) {
        filler.fill(this, args);
    }

    @Override
    public Iterable<AreaColumn> columns() {
        return new Iterable<AreaColumn>() {
            @Override
            public Iterator<AreaColumn> iterator() {
                return new AreaColumnIterator(DACVerticalArea.this);
            }
        };
    }

}
