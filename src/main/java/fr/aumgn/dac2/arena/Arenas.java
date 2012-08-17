package fr.aumgn.dac2.arena;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.World;

import fr.aumgn.bukkitutils.gconf.GConfLoadException;
import fr.aumgn.dac2.DAC;

public class Arenas {

    private Map<String, Arena> arenas;

    public Arenas(DAC dac) {
        load(dac);
    }

    public void load(DAC dac) {
        arenas = new HashMap<String, Arena>();

        try {
            Arena[] arenasList = dac.getPlugin().getGsonLoader()
                    .loadOrCreate("arenas.json", Arena[].class);
            for (Arena arena : arenasList) {
                arenas.put(arena.getName(), arena);
            }
        } catch (GConfLoadException exc) {
            dac.getPlugin().getLogger().severe("Unable to load arenas.json`");
        }
    }

    public void save(DAC dac) {
        try {
            dac.getPlugin().getGsonLoader().write(
                    "arenas.json", arenas.values());
        } catch (GConfLoadException exc) {
            dac.getPlugin().getLogger().severe("Unable to save arenas.json`");
        }
    }

    public Arena get(String name) {
        return arenas.get(name);
    }

    public void create(String name, World world) {
        arenas.put(name, new Arena(name, world));
    }
}
