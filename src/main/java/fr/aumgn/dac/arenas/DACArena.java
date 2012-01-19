package fr.aumgn.dac.arenas;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.sk89q.worldedit.bukkit.BukkitWorld;

import fr.aumgn.dac.arenas.region.DACRegion;
import fr.aumgn.dac.arenas.vector.DACLocation;

@SerializableAs("dac-arena")
public class DACArena implements ConfigurationSerializable {

	private String name;
	private boolean updated;
	private World world;
	private DivingBoard divingBoard;
	private Pool pool;
	private StartArea startArea;

	public DACArena(String name, World world) {
		this.name = name;
		updated = false;
		this.world = world;
		divingBoard = new DivingBoard(this);
		pool = new Pool(this);
		startArea = new StartArea(this);
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

	public static DACArena deserialize(Map<String, Object> map) {
		String world = (String)map.get("world");
		DACArena arena = new DACArena("", Bukkit.getWorld(world));
		arena.divingBoard.setDACLocation((DACLocation)map.get("diving-board"));
		arena.pool.setRegion((DACRegion) map.get("pool"));
		arena.startArea.setRegion((DACRegion) map.get("start-area"));
		return arena;
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("world", world.getName());
		map.put("diving-board", divingBoard.getDACLocation());
		map.put("pool", pool.getRegion());
		map.put("start-area", startArea.getRegion());
		return map;
	}

}
