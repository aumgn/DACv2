package fr.aumgn.dac.api.util;

import org.bukkit.Location;

import fr.aumgn.dac.api.area.AreaColumn;
import fr.aumgn.dac.api.stage.StagePlayer;

public class PlayerTeleporter {
    
    protected StagePlayer player;

    public PlayerTeleporter(StagePlayer player) {
        this.player = player;
    }
    
    private void teleport(Location location) {
        player.getPlayer().setFallDistance(0.0f);
        player.getPlayer().teleport(location);
    }

    public void toDiving() {
        teleport(this.player.getStage().getArena().getDivingBoard().getLocation());
    }
    
    public void toStart() {
        teleport(player.getStartLocation());
    }
    
    public void afterJump() {
        this.toStart();
    }
    
    public void afterFail() {
        this.toStart();
    }
    
    public void onTopOf(AreaColumn column) {
        Location loc = player.getPlayer().getLocation();
        teleport(new Location(
                column.getArea().getArena().getWorld(),
                column.getX(),
                column.getTop() + 1,
                column.getZ(),
                loc.getYaw(),
                loc.getPitch()
                ));
    }
    
}
