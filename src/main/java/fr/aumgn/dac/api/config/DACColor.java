package fr.aumgn.dac.api.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class DACColor {

    private ChatColor chatColor;
    private Material material;
    private byte data;

    public DACColor() {
    }

    public DACColor(ChatColor chatColor, Material material, byte data) {
        this.chatColor = chatColor;
        this.material = material;
        this.data = data;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public Material getMaterial() {
        return material;
    }

    public byte getData() {
        return data;
    }
}
