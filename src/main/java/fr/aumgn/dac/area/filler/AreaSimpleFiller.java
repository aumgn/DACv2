package fr.aumgn.dac.area.filler;

import org.bukkit.Material;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.area.Area;

public class AreaSimpleFiller implements AreaFiller {
	
	private BaseBlock baseBlock;
	
	public AreaSimpleFiller(Material material, byte data) {
		this.baseBlock = new BaseBlock(material.getId(), data);
	}

	public AreaSimpleFiller(Material material) {
		this.baseBlock = new BaseBlock(material.getId());
	}

	@Override
	public void fill(Area area) {
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
