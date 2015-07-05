package fr.aumgn.dac2.stage.join;

import com.google.common.collect.Maps;
import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.config.DACConfig;
import fr.aumgn.dac2.event.player.DACPlayerJoinEvent;
import fr.aumgn.dac2.exceptions.TooManyPlayers;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.game.start.StartStagePlayer;
import fr.aumgn.dac2.stage.Spectators;
import fr.aumgn.dac2.stage.Stage;
import fr.aumgn.dac2.stage.StagePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

import static fr.aumgn.dac2.utils.DACUtil.onlinePlayersIterable;

public class JoinStage implements Stage, Listener, GameStartData {

    private final DAC dac;
    private final Arena arena;
    private final Set<String> colorsTaken;
    private final Map<UUID, StagePlayer> players;
    private final Spectators spectators;

    public JoinStage(DAC dac, Arena arena) {
        this.dac = dac;
        this.arena = arena;
        this.colorsTaken = new HashSet<String>();
        this.players = Maps.newHashMap();
        this.spectators = new Spectators();
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public void start() {
        DACConfig config = dac.getConfig();
        PluginMessages messages = dac.getMessages();
        if (!config.initMessageHasRadius()) {
            Util.broadcast("dac2.spectator.watch",
                    messages.get("joinstage.start1", arena.getName()));
            Util.broadcast("dac2.spectator.watch",
                    messages.get("joinstage.start2"));
            return;
        }

        int radius = config.getInitMessageRadius();
        for (Player player : arena.getPlayersInRadius(radius)) {
            if (!player.hasPermission("dac2.spectator.watch")) {
                continue;
            }

            player.sendMessage(messages.get("joinstage.start1",
                    arena.getName()));
            player.sendMessage(messages.get("joinstage.start2"));
        }
    }

    @Override
    public void stop(boolean force) {
        if (force) {
            sendMessage(dac.getMessages().get("joinstage.stopped"));
        }
    }

    @Override
    public Listener[] getListeners() {
        return new Listener[0];
    }

    @Override
    public boolean contains(Player player) {
        return players.containsKey(player);
    }

    @Override
    public void sendMessage(String message) {
        for (Player player : onlinePlayersIterable(players.keySet())) {
            player.sendMessage(message);
        }

        spectators.send(dac, arena, message);
    }

    public void addPlayer(Player player, List<Color> colors) {
        Color finalColor = null;
        for (Color color : colors) {
            if (!colorsTaken.contains(color.name)) {
                finalColor = color;
                break;
            }
        }

        addPlayer(player, finalColor);
    }

    public void addPlayer(Player player, Color color) {
        if (players.size() >= dac.getColors().size()) {
            throw new TooManyPlayers(dac.getMessages()
                    .get("joinstage.toomanyplayers"));
        }

        if (color == null || colorsTaken.contains(color.name)) {
            color = getFirstColorAvailable();
        }

        StartStagePlayer stagePlayer = new StartStagePlayer(color, player);
        DACPlayerJoinEvent event = new DACPlayerJoinEvent(this, stagePlayer);
        Util.callEvent(event);

        PluginMessages msgs = dac.getMessages();
        String playerName = color.chat + player.getDisplayName();
        sendMessage(msgs.get("joinstage.join", playerName));

        players.put(player.getUniqueId(), stagePlayer);
        colorsTaken.add(color.name);

        list(player);
    }

    private Color getFirstColorAvailable() {
        List<Color> colors = dac.getColors().asList();
        for (Color color : colors) {
            if (!colorsTaken.contains(color.name)) {
                return color;
            }
        }

        throw new Error("A unexpected error occured ! Please report this to"
                + " the plugin author.");
    }

    public int size() {
        return players.size();
    }

    @Override
    public Set<StagePlayer> getPlayers() {
        return new HashSet<StagePlayer>(players.values());
    }

    @Override
    public Spectators getSpectators() {
        return spectators;
    }

    @Override
    public void list(CommandSender sender) {
        PluginMessages messages = dac.getMessages();

        sender.sendMessage(messages.get("joinstage.playerslist"));
        for (StagePlayer player : players.values()) {
            sender.sendMessage(messages.get("joinstage.playerentry", player.getDisplayName()));
        }
    }

    @Override
    public void onQuit(Player player) {
        StagePlayer data = players.remove(player);
        colorsTaken.remove(data.getColor().name);

        String message = dac.getMessages().get("joinstage.quit", player.getDisplayName());
        sendMessage(message);
        player.sendMessage(message);
    }
}
