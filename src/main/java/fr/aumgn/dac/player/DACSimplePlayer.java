package fr.aumgn.dac.player;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.dac.config.DACColor;
import fr.aumgn.dac.stage.Stage;

public class DACSimplePlayer implements DACPlayer {

	private Player player;
	private Stage stage;
	private String displayName;
	private DACColor color;
	private Location startLocation;
	
	public DACSimplePlayer(Player player, Stage stage, DACColor color, Location startLocation) {
		this.player = player;
		this.stage = stage;
		String name = ChatColor.stripColor(player.getDisplayName());
		this.displayName = color.getChatColor() + name + ChatColor.WHITE;
		this.startLocation = startLocation;
		this.color = color;
	}
	
	public DACSimplePlayer(DACPlayer player, Stage stage) {
		this(player.getPlayer(), stage, player.getColor(), player.getStartLocation());
	}

	@Override
	public Stage getStage() {
		return stage;
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public DACColor getColor() {
		return color;
	}

	@Override
	public Location getStartLocation() {
		return startLocation;
	}
	
	@Override
	public void tpToStart() {
		player.setFallDistance(0.0f);
		player.teleport(startLocation);
	}
	
	@Override
	public void tpToDiving() {
		player.setFallDistance(0.0f);
		player.teleport(stage.getArena().getDivingBoard().getLocation());
	}

}
