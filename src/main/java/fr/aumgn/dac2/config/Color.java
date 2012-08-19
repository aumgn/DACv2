package fr.aumgn.dac2.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Color {

    private final String name;
    private final ChatColor chat;
    private final Material block;
    private final short data;

    public Color(String name, ChatColor chat, Material material, short data) {
        this.name = name;
        this.chat = chat;
        this.block = material;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public ChatColor getChat() {
        return chat;
    }

    public Material getMaterial() {
        return block;
    }

    public short getData() {
        return data;
    }
}
