package fr.aumgn.dac.api.area;

import org.bukkit.Material;

/**
 * Represents a block in an {@link AreaColumn}.
 */
public interface AreaColumnBlock {

    int getIndex();

    Material getType();

    byte getData();

    void setType(Material material, byte data);

    void setType(Material material);

    void setData(byte data);

}
