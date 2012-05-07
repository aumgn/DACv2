package fr.aumgn.dac.api.fillstrategy;

import java.util.List;

import org.bukkit.Material;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.VerticalArea;

/**
 * Replaces all column of a {@link VerticalArea} with the given block. 
 */
public class FillFully implements FillStrategy {

    protected BaseBlock getBlock(List<String> args) {
        if (args.size() == 0) {
            return new BaseBlock(Material.STATIONARY_WATER.getId());
        }
        Material material = Material.matchMaterial(args.get(0));
        if (material == null) {
            material = Material.STATIONARY_WATER;
        }
        if (args.size() == 2) {
            return new BaseBlock(material.getId(), Byte.parseByte(args.get(1)));
        } else {
            return new BaseBlock(material.getId());
        }
    }

    @Override
    public void fill(VerticalArea area, List<String> args) {
        BaseBlock baseBlock = getBlock(args);
        EditSession editSession = new EditSession(area.getArena().getWEWorld(), -1);
        try {
            editSession.setBlocks(area.getWERegion(), baseBlock);
        } catch (MaxChangedBlocksException e) {
            String warning = "A weird exception occured while trying to fill ";
            warning += area.getArena().getName() + ". Maybe the area is too Big ?";
            DAC.getLogger().warning(warning);
        }
    }

}
