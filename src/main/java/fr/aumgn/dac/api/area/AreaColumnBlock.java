package fr.aumgn.dac.api.area;

import org.bukkit.Material;

public interface AreaColumnBlock {

    int getIndex();

    Material getType();

    byte getData();

    void setType(Material material);

    void setType(Material material, byte data);

    void setData(byte data);

}
