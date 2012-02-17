package fr.aumgn.dac.api.area;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;

import fr.aumgn.dac.api.arena.Arena;

public interface Area extends Iterable<Block> {

    Arena getArena();

    boolean contains(Location location);

    boolean contains(Player player);

    void update(Region region);

    Selection getSelection();

    Region getWERegion();

}
