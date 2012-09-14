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

    /**
     * Checks if this arena is complete.
     *
     * An arena is considered complete if it has :
     * <ul>
     *   <li>A pool</li>
     *   <li>A start region</li>
     *   <li>A diving</li>
     * </ul>
     */
    public boolean isComplete() {
        return pool != null && startRegion != null && diving != null;
    }

    public Pool getPool() {
        return pool;
    }

    /**
     * Returns the pool if defined or throws an exception if not.
     *
     * @param dac the main DAC instance.
     * @throws ArenaComponentUndefined
     * @return the pool
     */
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

    /**
     * Returns the start region if defined or throws an exception if not.
     *
     * @param dac the main DAC instance.
     * @throws ArenaComponentUndefined
     * @return the start region
     */

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

    /**
     * Returns the diving board if defined or throws an exception if not.
     *
     * @param dac the main DAC instance.
     * @throws ArenaComponentUndefined
     * @return the diving board
     */
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

    /**
     * Returns the surrounding region if defined or throws an exception if not.
     *
     * The surrounding region doesn't need to be manually defined.
     * It is also considered defined if the pool and the diving are.
     *
     * @param dac the main DAC instance.
     * @throws ArenaComponentUndefined
     * @return the surrounding region
     */
    public SurroundingRegion safeGetSurroundingRegion(DAC dac) {
        SurroundingRegion region = getSurroundingRegion();
        if (region == null) {
            throw new ArenaComponentUndefined(dac.getMessages()
                    .get("arena.surrounding.notdefined"));
        }

        return region;
    }

    /**
     * Sets the surrrounding region.
     *
     * This will override the region wich is automatically defined
     * using the pool and the diving.
     * 
     * @param surrounding the new manually defined surrounding region.
     */
    public void setSurroundingRegion(SurroundingRegion surrounding) {
        this.surrounding = surrounding;
        this.autoSurrounding = null;
    }
}
