package fr.aumgn.dac.plugin.area.filler;

import org.bukkit.Material;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.AreaFillStrategy;
import fr.aumgn.dac.plugin.area.DACArea;

public class AreaSimpleStrategy implements AreaFillStrategy {

	private BaseBlock baseBlock;

	public AreaSimpleStrategy(Material material, byte data) {
		this.baseBlock = new BaseBlock(material.getId(), data);
	}

	public AreaSimpleStrategy(Material material) {
		this.baseBlock = new BaseBlock(material.getId());
	}

	@Override
	public void fill(DACArea area) {
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
