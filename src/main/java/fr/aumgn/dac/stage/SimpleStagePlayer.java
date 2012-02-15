package fr.aumgn.dac.stage;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.dac.config.DACColor;

public class SimpleStagePlayer implements StagePlayer {

	private Player player;
	private Stage<? extends SimpleStagePlayer> stage;
	private String displayName;
	private DACColor color;
	private Location startLocation;
	
	public SimpleStagePlayer(Player player, Stage<? extends SimpleStagePlayer> stage, DACColor color, Location startLocation) {
		this.player = player;
		this.stage = stage;
		String name = ChatColor.stripColor(player.getDisplayName());
		this.displayName = color.getChatColor() + name + ChatColor.WHITE;
		this.startLocation = startLocation;
		this.color = color;
	}
	
	public SimpleStagePlayer(StagePlayer player, Stage<? extends SimpleStagePlayer> stage) {
		this(player.getPlayer(), stage, player.getColor(), player.getStartLocation());
	}

	@Override
	public Stage<?> getStage() {
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
	public void send(Object message) {
		player.sendMessage(message.toString());
	}
	
	@Override
	public void sendToOthers(Object message) {
		stage.send(message.toString(), this);
	}
	
	@Override
	public String formatForList() {
		return " " + displayName;
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
