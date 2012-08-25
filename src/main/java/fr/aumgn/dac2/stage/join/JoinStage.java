package fr.aumgn.dac2.stage.join;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdHashMap;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdMap;
import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.game.start.SimplePlayerData;
import fr.aumgn.dac2.stage.Stage;

public class JoinStage implements Stage, Listener, GameStartData {

    private final DAC dac;
    private final Arena arena;
    private final Map<String, Color> colors;
    private final PlayersIdMap<PlayerData> players;

    public JoinStage(DAC dac, Arena arena) {
        this.dac = dac;
        this.arena = arena;
        this.colors = dac.getColors().toMap();
        this.players = new PlayersIdHashMap<PlayerData>();
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public void start() {
        PluginMessages messages = dac.getMessages();
        Util.broadcast(messages.get("joinstage.start1", arena.getName()));
                //, "dac.game.watch");
        Util.broadcast(messages.get("joinstage.start2"));//, "dac.game.watch");
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
        for (Player player : players.playersSet()) {
            player.sendMessage(message);
        }
    }

    public void addPlayer(Player player, List<String> colorsName) {
        Color color = null;
        for (String rawColorName : colorsName) {
            String colorName = rawColorName.toLowerCase();
            if (colors.containsKey(colorName)) {
                color = colors.get(colorName);
                break;
            }
        }

        addPlayer(player, color);
    }

    public void addPlayer(Player player, Color color) {
        if (color == null || !colors.containsValue(color)) {
            // Get first color available
            color = colors.values().iterator().next();
        }

        PluginMessages msgs = dac.getMessages();
        String playerName = color.chat + player.getDisplayName();
        sendMessage(msgs.get("joinstage.join", playerName));

        players.put(player, new SimplePlayerData(color, player));
        colors.remove(color.name);

        player.sendMessage(msgs.get("joinstage.playerslist"));
        for (Entry<PlayerId, PlayerData> playerIG : players.entrySet()) {
            PlayerId playerId = playerIG.getKey();
            PlayerData data = playerIG.getValue();

            String name;
            if (playerId.isOnline()) {
                name = data.getColor().chat
                        + playerIG.getKey().getPlayer().getDisplayName();
            } else {
                name = ChatColor.ITALIC.toString() + data.getColor().chat
                        + playerId.getName();
            }

            player.sendMessage(msgs.get("joinstage.playerentry", name));
        }
    }

    public int size() {
        return players.size();
    }

    @Override
    public Map<PlayerId, PlayerData> getPlayersData() {
        return players;
    }

    @Override
    public Set<PlayerId> getSpectators() {
        return Collections.<PlayerId>emptySet();
    }
}
