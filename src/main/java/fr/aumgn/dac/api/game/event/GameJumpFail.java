package fr.aumgn.dac.api.game.event;

import org.bukkit.Location;
import org.bukkit.util.BlockVector;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.util.DACUtil;

public class GameJumpFail extends GameJumpEvent {

    private Location deathLocation;
    private boolean cancelDeath;

    public GameJumpFail(StagePlayer player, int x, int z) {
        super(player, x, z, DAC.getConfig().getTpAfterFail());
        deathLocation = player.getPlayer().getLocation();
        cancelDeath = true;
    }

    public BlockVector getRealDeathPos() {
        return DACUtil.getDamageBlockVector(deathLocation);
    }

    public boolean getCancelDeath() {
        return cancelDeath;
    }

    public void setCancelDeath(boolean cancelDeath) {
        this.cancelDeath = cancelDeath;
    }

}
