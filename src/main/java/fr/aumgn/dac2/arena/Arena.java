package fr.aumgn.dac2.arena;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.arena.regions.Region;
import fr.aumgn.dac2.arena.regions.StartRegion;

public class Arena {

    private final String name;
    private final UUID worldId;
    private Pool pool;
    private StartRegion startRegion;
    private Diving diving;

    public Arena(String name, World world) {
        this.name = name;
        this.worldId = world.getUID();
        this.pool = null;
        this.startRegion = null;
        this.diving = null;
    }

    public String getName() {
        return name;
    }

    public World getWorld() {
        return Bukkit.getWorld(worldId);
    }

    public Region getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public Region getStartRegion() {
        return startRegion;
    }

    public void setStartRegion(StartRegion region) {
        this.startRegion = region;
    }

    public Diving getDiving() {
        return diving;
    }

    public void setDiving(Diving diving) {
        this.diving = diving;
    }
}
