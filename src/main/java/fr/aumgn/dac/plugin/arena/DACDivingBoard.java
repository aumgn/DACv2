package fr.aumgn.dac.plugin.arena;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;

import fr.aumgn.dac.api.arena.DivingBoard;
import fr.aumgn.dac.plugin.area.vector.DACLocation;

@SerializableAs("dac-diving")
public class DACDivingBoard implements DivingBoard {

	private DACArena arena;
	private DACLocation dacLocation;

	public DACDivingBoard(DACArena arena) {
		this.arena = arena;
		this.dacLocation = new DACLocation();
	}

	public Object getDACLocation() {
		return dacLocation;
	}

	public void setDACLocation(DACLocation dacLocation) {
		this.dacLocation = dacLocation;
	}

	public Location getLocation() {
		return dacLocation.toLocation(arena.getWorld());
	}

	public void update(Location loc) {
		this.dacLocation = new DACLocation(loc);
		arena.updated();
	}

}
