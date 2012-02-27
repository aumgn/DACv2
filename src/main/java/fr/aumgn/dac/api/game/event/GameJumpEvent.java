package fr.aumgn.dac.api.game.event;

import com.sk89q.worldedit.BlockVector2D;

import fr.aumgn.dac.api.stage.StagePlayer;

public class GameJumpEvent extends GamePlayerEvent {

    private BlockVector2D pos;
    private boolean teleport;
    private boolean switchToNextTurn;
    private boolean cancelled;

    public GameJumpEvent(StagePlayer player, int x, int z, boolean teleport) {
        super(player);
        this.pos = new BlockVector2D(x, z);   
        this.teleport = teleport;
        this.switchToNextTurn = true;
        this.cancelled = false;
    }

    public BlockVector2D getPos() {
        return pos;
    }

    public int getX() {
        return pos.getBlockX();
    }

    public int getZ() {
        return pos.getBlockZ();
    }

    public boolean getMustTeleport() {
        return teleport;
    }

    public void setMustTeleport(boolean teleport) {
        this.teleport = teleport;
    }

    public boolean getSwitchToNextTurn() {
        return switchToNextTurn;
    }

    public void setSwitchToNextTurn(boolean bool) {
        this.switchToNextTurn = bool;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
