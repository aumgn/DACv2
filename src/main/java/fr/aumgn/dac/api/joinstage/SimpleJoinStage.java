package fr.aumgn.dac.api.joinstage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACColor;
import fr.aumgn.dac.api.config.DACColors;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.event.joinstage.DACJoinStageStartEvent;
import fr.aumgn.dac.api.event.joinstage.DACJoinStageStopEvent;
import fr.aumgn.dac.api.event.joinstage.DACPlayerJoinEvent;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.StagePlayer;
import fr.aumgn.dac.api.stage.StagePlayerManager;

public class SimpleJoinStage implements JoinStage<SimpleJoinStagePlayer> {

    private Arena arena;
    private DACColors colors;
    private Set<DACColor> colorsMap;
    private List<SimpleJoinStagePlayer> players;

    public SimpleJoinStage(Arena arena) {
        this.arena = arena;
        colors = DAC.getConfig().getColors();
        colorsMap = new HashSet<DACColor>();
        players = new ArrayList<SimpleJoinStagePlayer>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(DACMessage.JoinNewGame.format(arena.getName()));
            player.sendMessage(DACMessage.JoinNewGame2.getValue());
        }
        DAC.callEvent(new DACJoinStageStartEvent(this));
    }

    @Override
    public void registerAll() {
        StagePlayerManager playerManager = DAC.getPlayerManager();
        for (StagePlayer player : players) {
            playerManager.register(player);
        }
    }

    @Override
    public void unregisterAll() {
        StagePlayerManager playerManager = DAC.getPlayerManager();
        for (StagePlayer player : players) {
            playerManager.unregister(player);
        }
    }

    @Override
    public void send(Object msg, StagePlayer exclude) {
        for (StagePlayer player : players) {
            if (player != exclude) {
                player.getPlayer().sendMessage(msg.toString());
            }
        }
    }

    @Override
    public void send(Object msg) {
        send(msg, null);
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    private boolean isColorAvailable(String name) {
        DACColor color = colors.get(name);
        if (color == null) {
            return false;
        } else {
            return isColorAvailable(color);
        }
    }

    private boolean isColorAvailable(DACColor color) {
        return !colorsMap.contains(color);
    }

    private DACColor getFirstColorAvailable() {
        for (DACColor color : colors) {
            if (!colorsMap.contains(color)) {
                return color;
            }
        }
        // Should never be reached
        return colors.defaut();
    }

    private void addPlayer(Player player, DACColor color) {
        DACPlayerJoinEvent event = new DACPlayerJoinEvent(this, player, color, player.getLocation());
        DAC.callEvent(event);

        if (!event.isCancelled()) {
            SimpleJoinStagePlayer dacPlayer = new SimpleJoinStagePlayer(this, player, event.getColor(), event.getStartLocation());
            players.add(dacPlayer);
            DAC.getPlayerManager().register(dacPlayer);
            colorsMap.add(color);
            dacPlayer.send(DACMessage.JoinCurrentPlayers);
            for (StagePlayer currentPlayer : players) {
                dacPlayer.send(DACMessage.JoinPlayerList.format(currentPlayer.getDisplayName()));
            }
            dacPlayer.sendToOthers(DACMessage.JoinPlayerJoin.format(dacPlayer.getDisplayName()));
        }
    }

    @Override
    public void addPlayer(Player player, String[] colorsName) {
        int i = 0;
        DACColor color;
        while (i < colorsName.length && !isColorAvailable(colorsName[i])) {
            i++;
        }
        if (i == colorsName.length) {
            color = getFirstColorAvailable();
        } else {
            color = colors.get(colorsName[i]);
        }
        addPlayer(player, color);
    }

    @Override
    public void removePlayer(StagePlayer player) {
        send(DACMessage.JoinPlayerQuit.format(player.getDisplayName()));
        players.remove(player);
        DAC.getPlayerManager().unregister(player);
        colorsMap.remove(player.getColor());
        if (players.size() == 0) {
            stop();
        }
    }

    @Override
    public List<SimpleJoinStagePlayer> getPlayers() {
        return new ArrayList<SimpleJoinStagePlayer>(players);
    }

    @Override
    public void stop() {
        DAC.callEvent(new DACJoinStageStopEvent(this));
        DAC.getStageManager().unregister(this);
    }

    @Override
    public boolean isMinReached(GameMode<?> mode) {
        int minimum = mode.getClass().getAnnotation(DACGameMode.class).minPlayers();
        return players.size() >= minimum;
    }

    @Override
    public boolean isMaxReached() {
        return (players.size() >= DAC.getConfig().getMaxPlayers());
    }

}
