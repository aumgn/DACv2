package fr.aumgn.dac2.arena;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.google.gson.reflect.TypeToken;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.gson.GsonLoadException;
import fr.aumgn.dac2.DAC;

public class Arenas {

    private Map<String, Arena> arenas;

    public Arenas(DAC dac) {
        load(dac);
    }

    public void load(DAC dac) {
        arenas = new HashMap<String, Arena>();

        try {
            TypeToken<ArrayList<Arena>> typeToken =
                    new TypeToken<ArrayList<Arena>>() {};
            List<Arena> arenasList = dac.getPlugin().getGsonLoader()
                    .loadOrCreate("arenas.json", typeToken);
            for (Arena arena : arenasList) {
                arenas.put(arena.getName(), arena);
            }
        } catch (GsonLoadException exc) {
            dac.getLogger().severe("Unable to load `arenas.json`");
            throw new RuntimeException(exc);
        }
    }

    public void save(DAC dac) {
        try {
            dac.getPlugin().getGsonLoader().write(
                    "arenas.json", arenas.values());
        } catch (GsonLoadException exc) {
            dac.getPlugin().getLogger().severe("Unable to save arenas.json`");
        }
    }

    public boolean has(String name) {
        return arenas.containsKey(name);
    }

    public Arena get(String name) {
        return arenas.get(name);
    }

    public Arena get(Player player) {
        Vector pt = new Vector(player);
        for (Arena arena : arenas.values()) {
            if (arena.getStartRegion().contains(pt)) {
                return arena;
            }
        }

        return null;
    }

    public void create(String name, World world) {
        arenas.put(name, new Arena(name, world));
    }

    public void delete(Arena arena) {
        arenas.remove(arena.getName());
    }

    public List<Arena> all() {
        return Collections.unmodifiableList(new ArrayList<Arena>(
                arenas.values()));
    }

    public int length() {
        return arenas.values().size();
    }
}
