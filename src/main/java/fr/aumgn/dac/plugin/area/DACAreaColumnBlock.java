package fr.aumgn.dac.plugin.area;

import org.bukkit.Material;
import org.bukkit.block.Block;

import fr.aumgn.dac.api.area.AreaColumnBlock;

public class DACAreaColumnBlock implements AreaColumnBlock {

    private int index;
    private Block block;

    public DACAreaColumnBlock(int index, Block block) {
        this.index = index;
        this.block = block;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Material getType() {
        return block.getType();
    }

    @Override
    public byte getData() {
        return block.getData();
    }

    @Override
    public void setType(Material material) {
        block.setType(material);
    }

    @Override
    public void setType(Material material, byte data) {
        setType(material);
        setData(data);
    }

    @Override
    public void setData(byte data) {
        block.setData(data);
    }

}
