package fr.aumgn.dac.event.joinstage;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import fr.aumgn.dac.config.DACColor;
import fr.aumgn.dac.joinstage.JoinStage;

public class DACPlayerJoinEvent extends DACJoinStageEvent implements Cancellable {
	private static final long serialVersionUID = 1L;
	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private DACColor color;
	private Location startLocation;
	private boolean isCancelled = false;
	
	public DACPlayerJoinEvent(JoinStage<?> joinStage, Player player, DACColor color, Location location) {
		super("DACPlayerJoinEvent", joinStage);
		this.player = player;
		this.color = color;
		this.startLocation = location;
	}

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public Player getPlayer() {
    	return player;
    }

	public DACColor getColor() {
		return color;
	}

	public void setColor(DACColor color) {
		this.color = color;
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}
    
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		isCancelled = cancelled;
	}

}
