package fr.aumgn.dac.api.area;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.api.arena.Arena;

/**
 * Represents an area in a {@link Arena} arena.
 */
public interface Area {

    /**
     * Gets the {@link Arena} to which this area is associated.
     */
    Arena getArena();

    boolean contains(Player player);

    /**
     * Updates the area with the given WorldEdit region.
     * <p/>
     * For now, only CuboidRegion, Polygonal2DRegion and CylinderRegion are supported,
     * others will raise an exception.
     */
    void update(Region region);

    Selection getSelection();

    Region getWERegion();

}
