package fr.aumgn.dac.plugin.arena;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.bukkit.BukkitWorld;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.arena.DivingBoard;
import fr.aumgn.dac.api.arena.Pool;
import fr.aumgn.dac.api.arena.StartArea;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.plugin.area.region.DACRegion;
import fr.aumgn.dac.plugin.area.vector.DACLocation;

@SerializableAs("dac-arena")
public class DACArena implements Arena, ConfigurationSerializable {

    private String name;
    private boolean updated;
    private World world;
    private Set<String> modes;
    private DACDivingBoard divingBoard;
    private DACPool pool;
    private DACStartArea startArea;
    private GameOptions options;

    public DACArena(String name, World world) {
        this.name = name;
        updated = false;
        this.world = world;
        modes = DAC.getModes().getDefaults();
        divingBoard = new DACDivingBoard(this);
        pool = new DACPool(this);
        startArea = new DACStartArea(this);
        options = new GameOptions();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updated() {
        updated = true;
    }

    public boolean isUpdated() {
        return updated;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public BukkitWorld getWEWorld() {
        return new BukkitWorld(getWorld());
    }

    @Override
    public boolean hasMode(String name) {
        return modes.contains(name);
    }

    @Override
    public void addMode(String mode) {
        modes.add(mode);
        updated();
    }

    @Override
    public void removeMode(String mode) {
        modes.remove(mode);
        updated();
    }

    @Override
    public List<String> getModes() {
        return new ArrayList<String>(modes);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DivingBoard getDivingBoard() {
        return divingBoard;
    }

    @Override
    public Pool getPool() {
        return pool;
    }

    @Override
    public StartArea getStartArea() {
        return startArea;
    }

    @Override
    public Iterable<Entry<String, String>> options() {
        return options;
    }

    @Override
    public GameOptions mergeOptions(GameOptions options) {
        return this.options.merge(options);
    }

    @Override
    public void setOption(String name, String value) {
        options.set(name, value);
        updated();
    }

    @Override
    public void removeOption(String name) {
        options.remove(name);
        updated();
    }

    public static DACArena deserialize(Map<String, Object> map) {
        String world = (String) map.get("world");
        DACArena arena = new DACArena("", Bukkit.getWorld(world));
        if (map.containsKey("modes")) {
            Object modes = map.get("modes");
            if (modes instanceof List<?>) {
                List<?> modesValues = (List<?>) modes;
                arena.modes = new HashSet<String>();
                for (Object obj : modesValues) {
                    if (obj instanceof String) {
                        arena.modes.add((String) obj);
                    }
                }
            }
        }
        arena.divingBoard.setDACLocation((DACLocation) map.get("diving-board"));
        arena.pool.setRegion((DACRegion) map.get("pool"));
        arena.startArea.setRegion((DACRegion) map.get("start-area"));
        if (map.containsKey("options")) {
            arena.options = (GameOptions) map.get("options");
        }
        return arena;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("world", world.getName());
        map.put("modes", new ArrayList<String>(modes));
        map.put("diving-board", divingBoard.getDACLocation());
        map.put("pool", pool.getRegion());
        map.put("start-area", startArea.getRegion());
        map.put("options", options);
        return map;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DACArena other = (DACArena) obj;
        return name.equals(other.name);
    }

}
