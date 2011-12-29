package fr.aumgn.dac.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.sk89q.worldedit.bukkit.BukkitWorld;

public class DACArena implements ConfigurationSerializable {
	
	private String name;
	private boolean updated;
	private World world;
	private DivingBoard divingBoard;
	private Pool pool;
	private StartArea startArea;
	
	public DACArena(String name, World world) {
		this.name = name;
		updated = true;
		this.world = world;
		divingBoard = new DivingBoard(this);
		pool = new Pool(this);
		startArea = new StartArea(this);
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
		String name = (String)map.get("name");
		String world = (String)map.get("world");
		DACArena arena = new DACArena(name, Bukkit.getWorld(world));
		arena.getDivingBoard().load(map.get("diving-board"));
		arena.getPool().load(map.get("pool"));
		arena.getStartArea().load(map.get("start-area"));
		return arena;
	}
	
	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("world", world.getName());
		map.put("diving-board", divingBoard);
		map.put("pool", pool);
		map.put("start-area", startArea);
		return map;
	}

}
