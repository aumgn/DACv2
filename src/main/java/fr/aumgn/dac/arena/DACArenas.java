package fr.aumgn.dac.arena;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.area.region.DACCuboid;
import fr.aumgn.dac.area.region.DACCylinder;
import fr.aumgn.dac.area.region.DACPolygonal;
import fr.aumgn.dac.area.vector.DACBlockVector;
import fr.aumgn.dac.area.vector.DACBlockVector2D;
import fr.aumgn.dac.area.vector.DACLocation;
import fr.aumgn.dac.game.options.GameOptions;

public class DACArenas implements Iterable<DACArena> {

	static {
		ConfigurationSerialization.registerClass(DACArena.class);
		ConfigurationSerialization.registerClass(DACBlockVector.class);
		ConfigurationSerialization.registerClass(DACBlockVector2D.class);
		ConfigurationSerialization.registerClass(DACLocation.class);
		ConfigurationSerialization.registerClass(DACCuboid.class);
		ConfigurationSerialization.registerClass(DACPolygonal.class);
		ConfigurationSerialization.registerClass(DACCylinder.class);
		ConfigurationSerialization.registerClass(GameOptions.class);
	}

	private YamlConfiguration yaml;
	private boolean updated;
	private Map<String, DACArena> arenas;

	public DACArenas() {
		yaml = new YamlConfiguration();
		updated = false; 

		ensureDirectoryExists();
		try {
			yaml.load(getConfigFileName());
		} catch (IOException exc) {
			DAC.getLogger().warning("Unable to find " + getConfigFileName() + " config file");
		} catch (InvalidConfigurationException exception) {
			DAC.getLogger().warning("Unable to load " + getConfigFileName() + " config file");
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
		Plugin plugin = DAC.getPlugin();
		if (!plugin.getDataFolder().exists()) {
			try {
				plugin.getDataFolder().mkdir();
			} catch (SecurityException exc) {
				DAC.getLogger().warning("Unable to create " + plugin.getDataFolder() + " directory");
			}
		}
	}

	private String getConfigFileName() {
		return DAC.getPlugin().getDataFolder() + File.separator + "DAC.yml";
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
				DAC.getLogger().severe("Unable to save " + getConfigFileName() + " config file");
			}
		}
	}

	@Override
	public Iterator<DACArena> iterator() {
		return arenas.values().iterator();
	}

}
