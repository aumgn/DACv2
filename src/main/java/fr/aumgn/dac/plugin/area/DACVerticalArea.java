package fr.aumgn.dac.plugin.area;

import java.util.Iterator;

import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.area.VerticalArea;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;
import fr.aumgn.dac.plugin.arena.DACArena;

public class DACVerticalArea extends DACArea implements VerticalArea {

	public DACVerticalArea(DACArena arena) {
		super(arena);
	}

	public int getBottom() {
		return getWERegion().getMinimumPoint().getBlockY();
	}

	public int getTop() {
		return getWERegion().getMaximumPoint().getBlockY();
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
				return new AreaVerticalIterator(DACVerticalArea.this);
			}
		};
	}	
	
}