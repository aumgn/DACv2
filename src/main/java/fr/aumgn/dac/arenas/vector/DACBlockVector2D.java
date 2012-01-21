package fr.aumgn.dac.arenas.vector;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.Vector2D;

@SerializableAs("dac-blockvector2d")
public class DACBlockVector2D extends BlockVector2D implements ConfigurationSerializable {

	public DACBlockVector2D() {
		super(0, 0);
	}
	
	public DACBlockVector2D(Vector2D vector) {
		super(vector);
	}

	public static DACBlockVector2D deserialize(Map<String, Object> map) {
		int x, z;
		x = (Integer) map.get("x");
		z = (Integer) map.get("z");
		return new DACBlockVector2D(new BlockVector2D(x, z));
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("x", getBlockX());
		map.put("z", getBlockZ());
		return map;
	}

}
