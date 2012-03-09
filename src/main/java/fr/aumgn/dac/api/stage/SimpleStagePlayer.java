package fr.aumgn.dac.api.stage;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.dac.api.config.DACColor;
import fr.aumgn.dac.api.util.PlayerTeleporter;

public class SimpleStagePlayer implements StagePlayer {

    private Player player;
    private Stage stage;
    private String displayName;
    private DACColor color;
    private Location startLocation;

    public SimpleStagePlayer(Player player, Stage stage, DACColor color, Location startLocation) {
        this.player = player;
        this.stage = stage;
        String name = ChatColor.stripColor(player.getDisplayName());
        this.displayName = color.getChatColor() + name + ChatColor.WHITE;
        this.startLocation = startLocation;
        this.color = color;
    }

    public SimpleStagePlayer(StagePlayer player, Stage stage) {
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
    public void send(Object message) {
        player.sendMessage(message.toString());
    }

    @Override
    public String formatForList() {
        return " " + displayName;
    }

    @Override
    public PlayerTeleporter teleporter() {
        return new PlayerTeleporter(this);
    }

}
