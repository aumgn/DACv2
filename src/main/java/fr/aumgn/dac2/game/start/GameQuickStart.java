package fr.aumgn.dac2.game.start;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.playerid.PlayerId;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdHashMap;
import fr.aumgn.bukkitutils.playerid.map.PlayersIdMap;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.regions.StartRegion;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.exceptions.TooManyPlayers;

public class GameQuickStart implements GameStartData {

    private final Arena arena;
    private final PlayersIdMap<PlayerData> playersData;

    public GameQuickStart(DAC dac, Arena arena) {
        this.arena = arena;
        this.playersData = new PlayersIdHashMap<PlayerData>();

        StartRegion startRegion = arena.getStartRegion();
        Iterator<Color> colors = dac.getColors().iterator();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (startRegion.contains(player)) {
                if (!colors.hasNext()) {
                    throw new TooManyPlayers(dac.getMessages()
                            .get("quickstart.toomanyplayers"));
                }

                Color color = colors.next();
                PlayerData playerData = new SimplePlayerData(color, player);
                playersData.put(player, playerData);
            }
        }
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public Map<PlayerId, ? extends PlayerData> getPlayersData() {
        return playersData;
    }

    @Override
    public Set<PlayerId> getSpectators() {
        return Collections.<PlayerId>emptySet();
    }

    public int size() {
        return playersData.size();
    }
}
