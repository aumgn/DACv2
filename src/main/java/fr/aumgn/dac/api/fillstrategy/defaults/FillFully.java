package fr.aumgn.dac.api.fillstrategy.defaults;

import org.bukkit.Material;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.VerticalArea;
import fr.aumgn.dac.api.fillstrategy.DACFillStrategy;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;

@DACFillStrategy(name="fully")
public class FillFully implements FillStrategy {

	protected BaseBlock getBlock(String[] args) {
		if (args.length == 0) {
			return new BaseBlock(Material.STATIONARY_WATER.getId());
		}
		Material material = Material.matchMaterial(args[0]);
		System.out.println(material);
		if (material == null) {
			material = Material.STATIONARY_WATER;
		}
		if (args.length == 2) {
			return new BaseBlock(material.getId(), Byte.parseByte(args[1]));
		} else {
			return new BaseBlock(material.getId());
		}
	}

	@Override
	public void fill(VerticalArea area, String[] args) {
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
