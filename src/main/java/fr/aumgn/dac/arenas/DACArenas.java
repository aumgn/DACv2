package fr.aumgn.dac.arenas;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.region.DACCuboid;
import fr.aumgn.dac.arenas.region.DACCylinder;
import fr.aumgn.dac.arenas.region.DACPolygonal;
import fr.aumgn.dac.arenas.vector.DACBlockVector;
import fr.aumgn.dac.arenas.vector.DACBlockVector2D;
import fr.aumgn.dac.arenas.vector.DACLocation;

public class DACArenas {
	
	static {
		ConfigurationSerialization.registerClass(DACArena.class);
		ConfigurationSerialization.registerClass(DACBlockVector.class);
		ConfigurationSerialization.registerClass(DACBlockVector2D.class);
		ConfigurationSerialization.registerClass(DACLocation.class);
		ConfigurationSerialization.registerClass(DACCuboid.class);
		ConfigurationSerialization.registerClass(DACPolygonal.class);
		ConfigurationSerialization.registerClass(DACCylinder.class);
	}

	private DAC plugin;
	private YamlConfiguration yaml;
	private boolean updated;
	private Map<String, DACArena> arenas;

	public DACArenas(DAC dac) {
		plugin = dac;
		yaml = new YamlConfiguration();
		updated = false; 

		ensureDirectoryExists();
		try {
			yaml.load(getConfigFileName());
		} catch (IOException exc) {
			DAC.getDACLogger().warning("Unable to find " + getConfigFileName() + " config file");
		} catch (InvalidConfigurationException exception) {
			DAC.getDACLogger().warning("Unable to load " + getConfigFileName() + " config file");
		}		
		arenas = new HashMap<String, DACArena>();
		Set<String> arenaNames = yaml.getKeys(false);
		for (String name : arenaNames) {
			DACArena arena = (DACArena)yaml.get(name);
			arena.setName(name);
			arenas.put(name, arena);
		}
	}

	private void ensureDirectoryExists() {
		if (!plugin.getDataFolder().exists()) {
			try {
				plugin.getDataFolder().mkdir();
			} catch (SecurityException exc) {
				DAC.getDACLogger().warning("Unable to create " + plugin.getDataFolder() + " directory");
			}
		}
	}

	public DAC getPlugin() {
		return plugin;
	}

	private String getConfigFileName() {
		return plugin.getDataFolder() + File.separator + "DAC.yml";
	}

	public void createArena(String name, World world) {
		arenas.put(name, new DACArena(name, world));
	}

	public void removeArena(DACArena arena) {
		yaml.set(arena.getName(), null);
		arenas.remove(arena.getName());
		updated = true;
	}

	public DACArena get(String name) {
		return arenas.get(name);
	}

	public DACArena get(Player player) {
		return get(player.getLocation());
	}

	public DACArena get(Location location) {
		for (DACArena arena : arenas.values()) {
			if (arena.getStartArea().contains(location)) {
				return arena;				
			}
		}
		return null;
	}

	public void dump() {
		boolean needSave = updated;
		for (DACArena arena : arenas.values()) {
			if (arena.isUpdated()) {
				needSave = true;
				yaml.set(arena.getName(), arena);
			}
		}
		if (needSave) {
			try {
				ensureDirectoryExists();
				yaml.save(getConfigFileName());
			} catch (IOException e) {
				DAC.getDACLogger().severe("Unable to save " + getConfigFileName() + " config file");
			}
		}
	}

}
