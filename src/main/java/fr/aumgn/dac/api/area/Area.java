package fr.aumgn.dac.api.area;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;

public interface Area extends Iterable<Block> {

	boolean contains(Location location);

	boolean contains(Player player);

	void fillWith(AreaFillStrategy areaAllButOneStrategy);

	void update(Region region);

	Selection getSelection();

}
