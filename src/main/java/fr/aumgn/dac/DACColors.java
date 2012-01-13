package fr.aumgn.dac;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class DACColors implements Iterable<DACColors.DACColor>{
	
	public class DACColor {
		
		private String name;
		private ChatColor chatColor;
		private Material material; 
		private byte data;
		
		public DACColor(String name, ChatColor chatColor, Material material, byte data) {
			this.name = name;
			this.chatColor = chatColor;
			this.material = material;
			this.data = data;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ChatColor getChatColor() {
			return chatColor;
		}

		public void setChatColor(ChatColor chatColor) {
			this.chatColor = chatColor;
		}

		public Material getMaterial() {
			return material;
		}

		public void setMaterial(Material material) {
			this.material = material;
		}

		public byte getData() {
			return data;
		}

		public void setData(byte data) {
			this.data = data;
		}

	}
	
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
			if (section.isString("chat")) {
				chat = ChatColor.valueOf(section.getString("chat").toUpperCase());
			} else {
				return null;
			}
			if (section.isInt("block")) {
				int block = section.getInt("block");
				material = Material.getMaterial(block);
			} else if (section.isString("block")) {
				String block = section.getString("block");
				material = Material.valueOf(block.toUpperCase());
			} else { 
				material = Material.WOOL;
			}
			if (section.isInt("data")) {
				data = (byte) section.getInt("data");
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