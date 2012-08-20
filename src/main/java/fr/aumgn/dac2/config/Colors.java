package fr.aumgn.dac2.config;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import fr.aumgn.bukkitutils.gconf.GConfLoadException;
import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.DAC;

public class Colors {

    private final Color[] colors;

    public Colors(DAC dac) {
        Color[] colorsTmp;
        try {
            colorsTmp = dac.getPlugin().getGsonLoader()
                    .loadOrCreate("colors.json", Color[].class);
            if (colorsTmp.length < 2) {
                dac.getLogger().severe("Unable to load colors.json. " +
                        "Using defaults colors instead.");
                colorsTmp = getDefaults(dac.getConfig().getLocale());
            }
        } catch (GConfLoadException _) {
            colorsTmp = getDefaults(dac.getConfig().getLocale());
            try {
                dac.getPlugin().getGsonLoader()
                .write("colors.json", colorsTmp);
            } catch (GConfLoadException __) {
                dac.getLogger().info("Unable to write default color.json.");
            }
        }

        colors = colorsTmp;
    }

    public Color random() {
        return colors[Util.getRandom().nextInt(colors.length)];
    }

    public Map<String, Color> toMap() {
        Map<String, Color> map = new LinkedHashMap<String, Color>();
        for (Color color : colors) {
            map.put(color.name.toLowerCase(), color);
        }

        return map;
    }

    private static Color[] getDefaults(Locale locale) {
        if (locale.getLanguage().equals(Locale.FRENCH.getLanguage())) {
            return new Color[] {
                    createDefault("Rouge",   ChatColor.DARK_RED,     14),
                    createDefault("Vert",    ChatColor.DARK_GREEN,   13),
                    createDefault("Argent",  ChatColor.GRAY,          8),
                    createDefault("Violet",  ChatColor.DARK_PURPLE,  10),
                    createDefault("Orange",  ChatColor.GOLD,          1),
                    createDefault("Blanc",   ChatColor.WHITE,         0),
                    createDefault("Jaune",   ChatColor.YELLOW,        4),
                    createDefault("Magenta", ChatColor.RED,           2),
                    createDefault("Gris",    ChatColor.DARK_GRAY,     7),
                    createDefault("Citron",  ChatColor.GREEN,         5),
                    createDefault("Rose",    ChatColor.LIGHT_PURPLE,  6),
                    createDefault("Noir",    ChatColor.BLACK,        15)
            };
        }

        return new Color[] {
                createDefault("Red",     ChatColor.DARK_RED,     14),
                createDefault("Green",   ChatColor.DARK_GREEN,   13),
                createDefault("Silver",  ChatColor.GRAY,          8),
                createDefault("Purple",  ChatColor.DARK_PURPLE,  10),
                createDefault("Orange",  ChatColor.GOLD,          1),
                createDefault("White",   ChatColor.WHITE,         0),
                createDefault("Yellow",  ChatColor.YELLOW,        4),
                createDefault("Magenta", ChatColor.RED,           2),
                createDefault("Gray",    ChatColor.DARK_GRAY,     7),
                createDefault("Lemon",   ChatColor.GREEN,         5),
                createDefault("Pink",    ChatColor.LIGHT_PURPLE,  6),
                createDefault("Black",   ChatColor.BLACK,        15)
        };
    }

    private static Color createDefault(String name, ChatColor chat, int data) {
        return new Color(name, chat, Material.WOOL, (short) data);
    }
}
