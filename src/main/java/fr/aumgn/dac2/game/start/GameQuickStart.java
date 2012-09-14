package fr.aumgn.dac2.game.start;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.playerref.map.PlayersRefHashMap;
import fr.aumgn.bukkitutils.playerref.map.PlayersRefMap;
import fr.aumgn.bukkitutils.playerref.set.PlayersRefHashSet;
import fr.aumgn.bukkitutils.playerref.set.PlayersRefSet;
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
    public PlayersRefMap<? extends PlayerStartData> getPlayersData() {
        return playersData;
    }

    @Override
    public PlayersRefSet getSpectators() {
        return new PlayersRefHashSet();
    }

    public int size() {
        return playersData.size();
    }
}
