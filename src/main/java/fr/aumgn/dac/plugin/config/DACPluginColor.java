package fr.aumgn.dac.plugin.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import fr.aumgn.dac.api.config.DACColor;

public class DACPluginColor implements DACColor {

	private String name;
	private ChatColor chatColor;
	private Material material; 
	private byte data;

	public DACPluginColor(String name, ChatColor chatColor, Material material, byte data) {
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
