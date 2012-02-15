package fr.aumgn.dac.api.config;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import fr.aumgn.dac.api.DAC;

public class DACColors implements Iterable<DACColor> {

	private static final String CHAT_KEY = "chat";
	private static final String BLOCK_KEY = "block";
	private static final String DATA_KEY = "data";

	private Map<String, DACColor> colors;

	public DACColors(ConfigurationSection section, ConfigurationSection defColorsConfig) {
		colors = parseColors(section);
		Set<String> keys = section.getKeys(false);
		if (colors.size() < 2) {
			if (keys.size() > 0) {
				DAC.getLogger().warning("Unable to parse colors config, using defaults");
			}
			colors = parseColors(defColorsConfig);
		} else if (colors.size() < keys.size()) {
			DAC.getLogger().warning("Unable to parse all colors");
			DAC.getLogger().warning("Using " + colors.size() + " of " + keys.size());
		}
	}

	private Map<String, DACColor> parseColors(ConfigurationSection section) {
		Set<String> keys = section.getKeys(false);
		Map<String, DACColor> tmpColors = new LinkedHashMap<String, DACColor>();
		for (String key : keys) {
			if (section.isConfigurationSection(key)) {
				DACColor color = parseColor(key, section.getConfigurationSection(key));
				if (color == null) {
					DAC.getLogger().warning("Unable to parse " + key + " color.");
				} else { 
					tmpColors.put(key, color); 
				}
			}
		}
		return tmpColors;
	}

	private DACColor parseColor(String name, ConfigurationSection section) {
		try {
			ChatColor chat;
			Material material;
			byte data;
			if (section.isString(CHAT_KEY)) {
				chat = ChatColor.valueOf(section.getString(CHAT_KEY).toUpperCase());
			} else {
				return null;
			}
			if (section.isInt(BLOCK_KEY)) {
				int block = section.getInt(BLOCK_KEY);
				material = Material.getMaterial(block);
			} else if (section.isString(BLOCK_KEY)) {
				String block = section.getString(BLOCK_KEY);
				material = Material.valueOf(block.toUpperCase());
			} else { 
				material = Material.WOOL;
			}
			if (section.isInt(DATA_KEY)) {
				data = (byte) section.getInt(DATA_KEY);
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