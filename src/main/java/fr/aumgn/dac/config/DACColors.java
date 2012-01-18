package fr.aumgn.dac.config;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import fr.aumgn.dac.DAC;

public class DACColors implements Iterable<DACColor> {

	private static final String ChatKey = "chat";
	private static final String BlockKey = "block";
	private static final String DataKey = "data";

	private Map<String, DACColor> colors;

	public DACColors(ConfigurationSection section, ConfigurationSection defColorsConfig) {
		colors = parseColors(section);
		Set<String> keys = section.getKeys(false);
		if (colors.size() < 2) {
			if (keys.size() > 0) {
				DAC.getDACLogger().warning("Unable to parse colors config, using defaults");
			}
			colors = parseColors(defColorsConfig);
		} else if (colors.size() < keys.size()) {
			DAC.getDACLogger().warning("Unable to parse all colors");
			DAC.getDACLogger().warning("Using " + colors.size() + " of " + keys.size());
		}
	}

	private Map<String, DACColor> parseColors(ConfigurationSection section) {
		Set<String> keys = section.getKeys(false);
		Map<String, DACColor> colors = new LinkedHashMap<String, DACColor>();
		for (String key : keys) {
			if (section.isConfigurationSection(key)) {
				DACColor color = parseColor(key, section.getConfigurationSection(key));
				if (color != null) { colors.put(key, color); }
			}
		}
		return colors;
	}

	private DACColor parseColor(String name, ConfigurationSection section) {
		try {
			ChatColor chat;
			Material material;
			byte data;
			if (section.isString(ChatKey)) {
				chat = ChatColor.valueOf(section.getString(ChatKey).toUpperCase());
			} else {
				return null;
			}
			if (section.isInt(BlockKey)) {
				int block = section.getInt(BlockKey);
				material = Material.getMaterial(block);
			} else if (section.isString(BlockKey)) {
				String block = section.getString(BlockKey);
				material = Material.valueOf(block.toUpperCase());
			} else { 
				material = Material.WOOL;
			}
			if (section.isInt(DataKey)) {
				data = (byte) section.getInt(DataKey);
			} else {
				data = 0;
			}
			return new DACColor(name, chat, material, data);
		} catch (IllegalArgumentException exc) {
			return null;
		}
	}

	public DACColor get(String name) {
		return colors.get(name);
	}

	public DACColor defaut() {
		return colors.values().iterator().next();
	}

	@Override
	public Iterator<DACColor> iterator() {
		return colors.values().iterator();
	}

	public int size() {
		return colors.size();
	}

}