package fr.aumgn.dac.api.stage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACColor;
import fr.aumgn.dac.api.config.DACConfig;

public class SimpleStagePlayer implements StagePlayer {

    private Runnable tpToStart = new Runnable() {
        @Override
        public void run() {
            player.setFallDistance(0.0f);
            player.teleport(startLocation);
        }
    };

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
    public void sendToOthers(Object message) {
        stage.send(message.toString(), this);
    }

    @Override
    public String formatForList() {
        return " " + displayName;
    }

    @Override
    public void tpToStart() {
        tpToStart.run();
    }

    @Override
    public void tpAfterFail() {
        DACConfig config = DAC.getConfig();
        if (stage.getPlayers().size() > 1 && config.getTpAfterFail()) {
            int delay = config.getTpAfterFailDelay();
            if (delay == 0) {
                tpToStart.run();
            } else {
                Bukkit.getScheduler().scheduleAsyncDelayedTask(DAC.getPlugin(), tpToStart, delay);
            }
        }
    }

    @Override
    public void tpAfterJump() {
        tpToStart();
    }

    @Override
    public void tpToDiving() {
        player.setFallDistance(0.0f);
        player.teleport(stage.getArena().getDivingBoard().getLocation());
    }

}
