package fr.aumgn.dac2.stage.join;

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
import fr.aumgn.bukkitutils.playerid.set.PlayersIdHashSet;
import fr.aumgn.bukkitutils.playerid.set.PlayersIdSet;
import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.game.start.PlayerStartData;
import fr.aumgn.dac2.game.start.SimplePlayerStartData;
import fr.aumgn.dac2.stage.Stage;

public class JoinStage implements Stage, Listener, GameStartData {

    private final DAC dac;
    private final Arena arena;
    private final Map<String, Color> colors;
    private final PlayersIdMap<PlayerStartData> players;
    private final PlayersIdSet spectators;

    public JoinStage(DAC dac, Arena arena) {
        this.dac = dac;
        this.arena = arena;
        this.colors = dac.getColors().toMap();
        this.players = new PlayersIdHashMap<PlayerStartData>();
        this.spectators = new PlayersIdHashSet();
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public void start() {
        PluginMessages messages = dac.getMessages();
        Util.broadcast("dac2.game.watch", 
                messages.get("joinstage.start1", arena.getName()));
        Util.broadcast("dac2.game.watch", messages.get("joinstage.start2"));
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

        String spectatorMessage = dac.getConfig().getSpectatorsMsg()
                .format(new String[] { arena.getName(), message });
        for (Player spectator : spectators.players()) {
            spectator.sendMessage(spectatorMessage);
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

        players.put(player, new SimplePlayerStartData(color, player));
        colors.remove(color.name);

        player.sendMessage(msgs.get("joinstage.playerslist"));
        for (Entry<PlayerId, PlayerStartData> playerIG : players.entrySet()) {
            PlayerId playerId = playerIG.getKey();
            PlayerStartData data = playerIG.getValue();

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

    @Override
    public boolean isSpectator(Player player) {
        return spectators.contains(player);
    }

    @Override
    public void addSpectator(Player player) {
        spectators.add(player);
    }

    @Override
    public void removeSpectator(Player player) {
        spectators.remove(player);
    }

    public int size() {
        return players.size();
    }

    @Override
    public Map<PlayerId, PlayerStartData> getPlayersData() {
        return players;
    }

    @Override
    public Set<PlayerId> getSpectators() {
        return spectators;
    }
}
