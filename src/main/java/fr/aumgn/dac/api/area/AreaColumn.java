package fr.aumgn.dac.api.area;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public interface AreaColumn extends Iterable<Block> {

	World getWorld();

	int getX();

	int getZ();

	int getBottom();

	int getTop();

	void set(Material material, byte data);

	void set(Material material);

}
