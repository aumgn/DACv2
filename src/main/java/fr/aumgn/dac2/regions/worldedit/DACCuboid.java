package fr.aumgn.dac2.regions.worldedit;

import static fr.aumgn.dac2.utils.WEUtils.*;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.bukkitutils.geom.Vector;

public class DACCuboid extends DACWERegion {

    private final UUID worldId;
    private final Vector min;
    private final Vector max;

    public DACCuboid(CuboidRegion region) {
        this.worldId = weWorld2World(region.getWorld()).getUID();
        this.min = we2bu(region.getMinimumPoint());
        this.max = we2bu(region.getMaximumPoint());
    }

    @Override
    public String getType() {
        return "we-cuboid";
    }

    @Override
    protected Region createRegion() {
        World world = Bukkit.getWorld(worldId);
        return new CuboidRegion(BukkitUtil.getLocalWorld(world), bu2we(min),
                bu2we(max));
    }

    @Override
    public Selection getSelection(World world) {
        return new CuboidSelection(world, bu2we(min), bu2we(max));
    }
}
