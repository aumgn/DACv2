package fr.aumgn.dac.api.area;

import org.bukkit.Material;
import org.bukkit.World;

public interface AreaColumn extends Iterable<AreaColumnBlock> {

    World getWorld();

    int getX();

    int getZ();

    int getBottom();

    int getTop();

    int getHeight();

    AreaColumnBlock get(int index);

    void set(Material material);

    void set(Material material, byte data);

}
