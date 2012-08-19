package fr.aumgn.dac2.stage;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdHashMap;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdMap;
import fr.aumgn.bukkitutils.util.Util;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.config.Color;

public class JoinStage implements Stage, Listener {

    private final DAC dac;
    private final Arena arena;
    private final Map<String, Color> colors;
    private final PlayersIdMap<Color> players;

    public JoinStage(DAC dac, Arena arena) {
        this.dac = dac;
        this.arena = arena;
        this.colors = dac.getColors().toMap();
        this.players = new PlayersIdHashMap<Color>();
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
    public void stop() {
    }

    @Override
    public Listener[] getListeners() {
        return new Listener[0];
    }

    @Override
    public boolean contains(Player player) {
        return players.containsKey(player);
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

        players.put(player, color);
        colors.remove(color.getName());
    }
}
