package fr.aumgn.dac.arena;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.bukkit.BukkitWorld;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.area.region.DACRegion;
import fr.aumgn.dac.area.vector.DACLocation;
import fr.aumgn.dac.game.options.GameOptions;

@SerializableAs("dac-arena")
public class DACArena implements ConfigurationSerializable {

	private String name;
	private boolean updated;
	private World world;
	private Set<String> modes; 
	private DivingBoard divingBoard;
	private Pool pool;
	private StartArea startArea;
	private GameOptions options;

	public DACArena(String name, World world) {
		this.name = name;
		updated = false;
		this.world = world;
		modes = DAC.getModes().getDefaults();
		divingBoard = new DivingBoard(this);
		pool = new Pool(this);
		startArea = new StartArea(this);
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

	public World getWorld() {
		return world;
	}

	public BukkitWorld getWEWorld() {
		return new BukkitWorld(getWorld());
	}
	
	public boolean hasMode(String name) {
		return modes.contains(name);
	}

	public void addMode(String mode) {
		modes.add(mode);
		updated();
	}
	
	public void removeMode(String mode) {
		modes.remove(mode);
		updated();
	}

	public List<String> getModes() {
		return new ArrayList<String>(modes);
	}
	
	public String getName() {
		return name;
	}

	public DivingBoard getDivingBoard() {
		return divingBoard;
	}

	public Pool getPool() {
		return pool;
	}

	public StartArea getStartArea() {
		return startArea;
	}

	public GameOptions getOptions() {
		return options;
	}
	
	public static DACArena deserialize(Map<String, Object> map) {
		String world = (String)map.get("world");
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
		arena.divingBoard.setDACLocation((DACLocation)map.get("diving-board"));
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

}
