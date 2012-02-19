package fr.aumgn.dac.api.area;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.exception.InvalidRegionType;

/**
 * Represents an area in a {@link Arena} arena.
 */
public interface Area extends Iterable<Block> {

    /**
     * Gets the {@link Arena} to which this area is associated.
     * 
     * @return arena
     */
    Arena getArena();

    /**
     * Checks if the area contains the given location.
     * 
     * @param Location location to check
     * @return whether the location is inside or not
     */
    boolean contains(Location location);

    /**
     * Checks if the area contains the given player.
     * 
     * @param Player player to check
     * @return whether the player is inside or not
     */
    boolean contains(Player player);

    /**
     * Updates the area with the given WorldEdit region.
     * <p/>
     * For now, only CuboidRegion, Polygonal2DRegion and CylinderRegion are supported,
     * others will raise an exception.
     * 
     * @throws InvalidRegionType if region type is not supported.
     * @param region to update the area with
     */
    void update(Region region);

    /**
     * Gets the WorldEdit Selection associated with this area. 
     * 
     * @return WorldEdit selection for this area 
     */
    Selection getSelection();

    /**
     * Gets the WorldEdit Region associated with this area.    
     * 
     * @return WorldEdit region for this area 
     */
    Region getWERegion();

}
