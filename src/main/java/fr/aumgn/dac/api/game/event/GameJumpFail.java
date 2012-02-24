package fr.aumgn.dac.api.game.event;

import org.bukkit.Location;
import org.bukkit.util.BlockVector;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.util.DACUtil;

public class GameJumpFail extends GameJumpEvent {

	private BlockVector realDeathPos; 
	private boolean cancelDeath;
	private boolean hasLost;	
	
	public GameJumpFail(StagePlayer player, int x, int z) {
		super(player, x, z, DAC.getConfig().getTpAfterFail());
		Location loc = player.getPlayer().getLocation();
		this.realDeathPos = DACUtil.getDamageBlockVector(loc);
		cancelDeath = true;
		hasLost = false;
	}
	
	public BlockVector getRealDeathPos() {
		return realDeathPos;
	}
	
	public boolean getCancelDeath() {
		return cancelDeath;
	}
	
	public void setCancelDeath(boolean cancelDeath) {
		this.cancelDeath = cancelDeath;
	}
	
	public boolean getHasLost() {
		return hasLost;
	}
	
	public void setHasLost(boolean hasLost) {
		this.hasLost = hasLost;
	}

}
