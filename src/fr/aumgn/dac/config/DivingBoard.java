package fr.aumgn.dac.config;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

public class DivingBoard extends Location {
	
	private DACArena arena;
	
	public DivingBoard(DACArena arena) {
		super(arena.getWorld(), 0.0, 0.0, 0.0, 0.0f, 0.0f);
		this.arena = arena;
	}
	
	public DivingBoard(DACArena arena, ConfigurationSection section) {
		this(arena);
		if (section != null) {
			Vector vec = section.getVector("pos");
			if (vec != null) {
				setX(vec.getX());
				setY(vec.getY());
				setZ(vec.getZ());
			}
			setYaw((float)section.getDouble("yaw", 0.0));
			setPitch((float)section.getDouble("pitch", 0.0));
		}
	}
	
	public void update(Location loc) {
		setX(loc.getX());
		setY(loc.getY());
		setZ(loc.getZ());
		setYaw(loc.getYaw());
		setPitch(loc.getPitch());
		arena.updated();
	}

	public void dump(ConfigurationSection section) {
		section.set("pos", toVector());
		section.set("yaw", (double)getYaw());
		section.set("pitch", (double)getPitch());
	}

}
