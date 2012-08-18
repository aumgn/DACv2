package fr.aumgn.dac2.utils;

import org.bukkit.World;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.selections.RegionSelection;
import com.sk89q.worldedit.regions.EllipsoidRegion;
import com.sk89q.worldedit.regions.EllipsoidRegionSelector;

public class EllipsoidSelection extends RegionSelection {

    public EllipsoidSelection(World world, Vector center, Vector radius) {
        super(world);

        EllipsoidRegionSelector sel = new EllipsoidRegionSelector(
                BukkitUtil.getLocalWorld(world));
        sel.selectPrimary(center);
        sel.selectSecondary(center.add(radius.getBlockX(), 0, 0));
        sel.selectSecondary(center.add(0, radius.getBlockY(), 0));
        sel.selectSecondary(center.add(0, 0, radius.getBlockZ()));

        EllipsoidRegion region = sel.getIncompleteRegion();
        setRegionSelector(sel);
        setRegion(region);
    }
}
