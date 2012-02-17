package fr.aumgn.dac.api.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public interface DACColor {

    String getName();

    ChatColor getChatColor();

    Material getMaterial();

    byte getData();

}
