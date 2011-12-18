package fr.aumgn.dac.config;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class DACArena {
	
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

	public DACArena(String name, ConfigurationSection section) {
		this.name = name;
		updated = false;
		world = Bukkit.getWorld(section.getString("world", "world"));
		divingBoard = new DivingBoard(this, section.getConfigurationSection("diving-board"));
		pool = new Pool(this, section.getConfigurationSection("pool"));
		startArea = new StartArea(this, section.getConfigurationSection("start-area"));
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

	public void dump(ConfigurationSection arenaSection) {
		ConfigurationSection section;
		arenaSection.set("world", world.getName());
		section = arenaSection.createSection("diving-board");
		divingBoard.dump(section);
		section = arenaSection.createSection("pool");
		pool.dump(section);
		section = arenaSection.createSection("start-area");
		startArea.dump(section);
	}

}
