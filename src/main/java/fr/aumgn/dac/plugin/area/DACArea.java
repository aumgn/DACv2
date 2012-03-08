package fr.aumgn.dac.plugin.area;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.CylinderRegion;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.api.area.Area;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.exception.InvalidRegionType;
import fr.aumgn.dac.plugin.area.region.DACCuboid;
import fr.aumgn.dac.plugin.area.region.DACCylinder;
import fr.aumgn.dac.plugin.area.region.DACPolygonal;
import fr.aumgn.dac.plugin.area.region.DACRegion;
import fr.aumgn.dac.plugin.arena.DACArena;

public class DACArea implements Area {

    private DACArena arena;
    private DACRegion region;

    public DACArea(DACArena arena) {
        this.arena = arena;
        this.region = new DACCuboid();
    }

    @Override
    public void update(Region region) {
        if (region instanceof CuboidRegion) {
            CuboidRegion cuboid = (CuboidRegion) region;
            this.region = new DACCuboid(cuboid);
        } else if (region instanceof Polygonal2DRegion) {
            Polygonal2DRegion poly = (Polygonal2DRegion) region;
            this.region = new DACPolygonal(poly);
        } else if (region instanceof CylinderRegion) {
            CylinderRegion cyl = (CylinderRegion) region;
            this.region = new DACCylinder(cyl);
        } else {
            throw new InvalidRegionType("CuboidRegion", "Polygonal2DRegion", region.getClass());
        }
        arena.updated();
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    public DACRegion getRegion() {
        return region;
    }

    @Override
    public Region getWERegion() {
        return region.getRegion(arena.getWEWorld());
    }

    public void setRegion(DACRegion region) {
        this.region = region;
    }

    @Override
    public Selection getSelection() {
        return region.getSelection(arena.getWorld());
    }

    @Override
    public boolean contains(Player player) {
        return contains(player.getLocation());
    }

    @Override
    public boolean contains(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        return region.getRegion(arena.getWEWorld()).contains(new Vector(x, y, z));
    }

    @Override
    public Iterator<Block> iterator() {
        return new AreaIterator(this, getWERegion().iterator());
    }

}
