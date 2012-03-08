package fr.aumgn.dac.plugin.area.vector;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.Vector;

@SerializableAs("dac-blockvector")
public class DACBlockVector implements ConfigurationSerializable {

    private int x;
    private int y;
    private int z;

    private DACBlockVector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public DACBlockVector() {
        this(0, 0, 0);
    }

    public DACBlockVector(Vector vector) {
        this(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public static DACBlockVector deserialize(Map<String, Object> map) {
        int x, y, z;
        x = (Integer) map.get("x");
        y = (Integer) map.get("y");
        z = (Integer) map.get("z");
        return new DACBlockVector(new Vector(x, y, z));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        return map;
    }

    public Vector getVector() {
        return new Vector(x, y, z);
    }

}
