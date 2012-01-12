package fr.aumgn.dac;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
	
	private List<DACColor> colors;

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
	
	private List<DACColor> parseColors(ConfigurationSection section) {
		Set<String> keys = section.getKeys(false);
		List<DACColor> colors = new LinkedList<DACColor>();
		for (String key : keys) {
			if (section.isConfigurationSection(key)) {
				DACColor color = parseColor(key, section.getConfigurationSection(key));
				if (color != null) { colors.add(color); }
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

	@Override
	public Iterator<DACColor> iterator() {
		return colors.iterator();
	}
	
}