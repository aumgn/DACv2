package fr.aumgn.dac.plugin.area.vector;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.Vector2D;

@SerializableAs("dac-blockvector2d")
public class DACBlockVector2D implements ConfigurationSerializable {

    private int x;
    private int z;

    public DACBlockVector2D(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public DACBlockVector2D() {
        this(0, 0);
    }

    public DACBlockVector2D(Vector2D vector) {
        this(vector.getBlockX(), vector.getBlockZ());
    }

    public static DACBlockVector2D deserialize(Map<String, Object> map) {
        int x, z;
        x = (Integer) map.get("x");
        z = (Integer) map.get("z");
        return new DACBlockVector2D(x, z);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("x", x);
        map.put("z", z);
        return map;
    }

    public BlockVector2D getVector() {
        return new BlockVector2D(x, z);
    }

}
