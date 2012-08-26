package fr.aumgn.dac2.arena;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.regions.Pool;
import fr.aumgn.dac2.arena.regions.StartRegion;
import fr.aumgn.dac2.arena.regions.SurroundingRegion;
import fr.aumgn.dac2.exceptions.ArenaComponentUndefined;

public class Arena {

    private final String name;
    private final UUID worldId;
    private Pool pool;
    private StartRegion startRegion;
    private Diving diving;
    private SurroundingRegion surrounding;

    private transient SurroundingRegion autoSurrounding;

    public Arena(String name, World world) {
        this.name = name;
        this.worldId = world.getUID();
        this.pool = null;
        this.startRegion = null;
        this.diving = null;
        this.surrounding = null;
        this.autoSurrounding = null;
    }

    public String getName() {
        return name;
    }

    public World getWorld() {
        return Bukkit.getWorld(worldId);
    }

    public boolean isIn(World world) {
        return worldId.equals(world.getUID());
    }

    public boolean isComplete() {
        return pool != null && startRegion != null && diving != null;
    }

    public Pool getPool() {
        return pool;
    }

    public Pool safeGetPool(DAC dac) {
        if (pool == null) {
            throw new ArenaComponentUndefined(dac.getMessages()
                    .get("arena.pool.notdefined"));
        }

        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
        this.surrounding = null;
    }

    public StartRegion getStartRegion() {
        return startRegion;
    }

    public StartRegion safeGetStartRegion(DAC dac) {
        if (startRegion == null) {
            throw new ArenaComponentUndefined(dac.getMessages()
                    .get("arena.start.notdefined"));
        }

        return startRegion;
    }

    public void setStartRegion(StartRegion region) {
        this.startRegion = region;
    }

    public Diving getDiving() {
        return diving;
    }

    public Diving safeGetDiving(DAC dac) {
        if (diving == null) {
            throw new ArenaComponentUndefined(dac.getMessages()
                    .get("arena.diving.notdefined"));
        }

        return diving;
    }

    public void setDiving(Diving diving) {
        this.diving = diving;
        this.surrounding = null;
    }

    public SurroundingRegion getSurroundingRegion() {
        if (surrounding != null) {
            return surrounding;
        }

        if (autoSurrounding == null) {
            if (diving == null || pool == null) {
                return null;
            }

            autoSurrounding = new SurroundingRegion(diving, pool);
        }
        return autoSurrounding;
    }

    public SurroundingRegion safeGetSurroundingRegion(DAC dac) {
        SurroundingRegion region = getSurroundingRegion();
        if (region == null) {
            throw new ArenaComponentUndefined(dac.getMessages()
                    .get("arena.surrounding.notdefined"));
        }

        return region;
    }

    public void setSurroundingRegion(SurroundingRegion surrounding) {
        this.surrounding = surrounding;
        this.autoSurrounding = null;
    }
}
