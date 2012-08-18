package fr.aumgn.dac2.utils;

import org.bukkit.World;

import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.selections.RegionSelection;
import com.sk89q.worldedit.regions.CylinderRegion;
import com.sk89q.worldedit.regions.CylinderRegionSelector;

public class CylinderSelection extends RegionSelection {

    public CylinderSelection(World world, Vector2D center,
            Vector2D radius, int minY, int maxY) {
        super(world);

        CylinderRegionSelector sel = new CylinderRegionSelector(
                BukkitUtil.getLocalWorld(world));
        sel.selectPrimary(center.toVector(minY));
        sel.selectSecondary(center.toVector(minY)
                .add(radius.getBlockX(), 0, 0));
        sel.selectSecondary(center.toVector(maxY)
                .add(0, 0, radius.getBlockZ()));

        CylinderRegion cyl = sel.getIncompleteRegion();
        setRegionSelector(sel);
        setRegion(cyl);
    }
}
