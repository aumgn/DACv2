package fr.aumgn.dac.arenas;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class DivingBoard extends Location implements ConfigurationSerializable {

	private DACArena arena;

	public DivingBoard(DACArena arena) {
		super(arena.getWorld(), 0.0, 0.0, 0.0, 0.0f, 0.0f);
		this.arena = arena;
	}

	@SuppressWarnings("unchecked")
	public void load(Object data) {
		if (data instanceof Map) {
			Map<String, Object> map = (Map<String, Object>)data;
			setX((Double)map.get("x"));
			setY((Double)map.get("y"));
			setZ((Double)map.get("z"));
			double yaw = (Double)map.get("yaw");
			double pitch = (Double)map.get("pitch");
			setYaw((float)yaw);
			setPitch((float)pitch);
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

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("x", getX());
		map.put("y", getY());
		map.put("z", getZ());
		map.put("yaw", getYaw());
		map.put("pitch", getPitch());
		return map;
	}

}
