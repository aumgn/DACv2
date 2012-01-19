package fr.aumgn.dac.arenas;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;

import fr.aumgn.dac.arenas.vector.DACLocation;

@SerializableAs("dac-diving")
public class DivingBoard {

	private DACArena arena;
	private DACLocation dacLocation;

	public DivingBoard(DACArena arena) {
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
