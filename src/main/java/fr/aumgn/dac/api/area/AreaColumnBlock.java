package fr.aumgn.dac.api.area;

import org.bukkit.Material;

/**
 * Represents a block in an {@link AreaColumn}.
 */
public interface AreaColumnBlock {

    /**
     * Gets the index of this block in the column.
     * 
     * @return the index of this block in the column
     */
    int getIndex();

    /**
     * Gets the type (Material) of the represented block.
     * 
     * @return the Material of the block
     */
    Material getType();

    /**
     * Gets the data of the represented block.
     * 
     * @return the data associated to this block
     */
    byte getData();
    
    /**
     * Sets the type (Material) and data of this block.
     * 
     * @param material The material to set this block to.
     */
    void setType(Material material, byte data);

    /**
     * @see #setType(Material, byte)
     */
    void setType(Material material);

    /**
     * @see #setType(Material, byte)
     */
    void setData(byte data);

}
