package fr.aumgn.dac.arenas.vector;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("dac-location")
public class DACLocation implements ConfigurationSerializable {

	private double x;
	private double y;
	private double z;
	private float pitch;
	private float yaw;

	public DACLocation() {
		x = 0.0;
		y = 0.0;
		z = 0.0;
		pitch = 0.0f;
		yaw = 0.0f;
	}
	
	public DACLocation(Location bLocation) {
		x = bLocation.getX();
		y = bLocation.getY();
		z = bLocation.getZ();
		pitch = bLocation.getPitch();
		yaw = bLocation.getYaw();
	}
	
	public static DACLocation deserialize(Map<String, Object> map) {
		DACLocation loc = new DACLocation();
		loc.x = (Double) map.get("x");
		loc.y = (Double) map.get("y");
		loc.z = (Double) map.get("z");
		Double pitch = (Double)map.get("pitch");
		loc.pitch = pitch.floatValue(); 
		Double yaw = (Double)map.get("yaw");
		loc.pitch = yaw.floatValue(); 
		return loc;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("x", x);
		map.put("y", y);
		map.put("z", z);
		map.put("pitch", Double.valueOf(pitch));
		map.put("yaw", Double.valueOf(yaw));
		return map;
	}
	
	public Location toLocation(World world) {
		return new Location(world, x, y, z, pitch, yaw);
	}

}
