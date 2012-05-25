package fr.aumgn.dac.plugin.config;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACColor;
import fr.aumgn.dac.api.config.DACColors;

public class DACPluginColors extends LinkedHashMap<String, DACColor> implements DACColors {

    public DACPluginColors() {
        super();
    }

    @Override
    public DACColor first() {
        return values().iterator().next();
    }

    @Override
    public Set<Entry<String, DACColor>> colors() {
        return entrySet();
    }

    @Override
    public DACColor random() {
        int i = DAC.getRand().nextInt(size());
        Iterator<DACColor> it = values().iterator();
        while (i > 0) {
            it.next();
            i--;
        }
        return it.next();
    }

    @Override
    public DACColor get(String name) {
        return super.get(name);
    }

    public static DACPluginColors getDefaults() {
        DACPluginColors colors = new DACPluginColors();
        colors.addDefault("rouge",    ChatColor.DARK_RED,     14);
        colors.addDefault("vert",     ChatColor.DARK_GREEN,   13);
        colors.addDefault("argent",   ChatColor.GRAY,          8);
        colors.addDefault("violet",   ChatColor.DARK_PURPLE,  10);
        colors.addDefault("orange",   ChatColor.GOLD,          1);
        colors.addDefault("blanc",    ChatColor.WHITE,         0);
        colors.addDefault("jaune",    ChatColor.YELLOW,        4);
        colors.addDefault("magenta",  ChatColor.RED,           2);
        colors.addDefault("gris",     ChatColor.DARK_GRAY,     7);
        colors.addDefault("citron",   ChatColor.GREEN,         5);
        colors.addDefault("rose",     ChatColor.LIGHT_PURPLE,  6);
        colors.addDefault("noir",     ChatColor.BLACK,        15);
        return colors;
    }

    private void addDefault(String name, ChatColor chat, int data) {
        put(name, new DACColor(chat, Material.WOOL, (byte) data));
    }
}
