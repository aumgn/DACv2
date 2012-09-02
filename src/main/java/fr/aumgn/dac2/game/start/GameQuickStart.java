package fr.aumgn.dac2.game.start;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.playerref.PlayerRef;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefHashMap;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefMap;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.regions.StartRegion;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.exceptions.TooManyPlayers;

public class GameQuickStart implements GameStartData {

    private final Arena arena;
    private final PlayersRefMap<PlayerStartData> playersData;

    public GameQuickStart(DAC dac, Arena arena) {
        this.arena = arena;
        this.playersData = new PlayersRefHashMap<PlayerStartData>();

        StartRegion startRegion = arena.getStartRegion();
        Iterator<Color> colors = dac.getColors().iterator();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (startRegion.contains(player)) {
                if (!colors.hasNext()) {
                    throw new TooManyPlayers(dac.getMessages()
                            .get("quickstart.toomanyplayers"));
                }

                Color color = colors.next();
                PlayerStartData playerData = new SimplePlayerStartData(color, player);
                playersData.put(player, playerData);
            }
        }
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public Map<PlayerRef, ? extends PlayerStartData> getPlayersData() {
        return playersData;
    }

    @Override
    public Set<PlayerRef> getSpectators() {
        return Collections.<PlayerRef>emptySet();
    }

    public int size() {
        return playersData.size();
    }
}
