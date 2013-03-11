package fr.aumgn.dac2.game.start;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.playerref.set.PlayersRefHashSet;
import fr.aumgn.bukkitutils.playerref.set.PlayersRefSet;
import fr.aumgn.dac2.DAC;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.arena.regions.StartRegion;
import fr.aumgn.dac2.config.Color;
import fr.aumgn.dac2.exceptions.TooManyPlayers;
import fr.aumgn.dac2.stage.StagePlayer;

public class GameQuickStart implements GameStartData {

    private final Arena arena;
    private final Set<StagePlayer> players;

    public GameQuickStart(DAC dac, Arena arena) {
        this.arena = arena;
        this.players = new HashSet<StagePlayer>();

        StartRegion startRegion = arena.getStartRegion();
        Iterator<Color> colors = dac.getColors().iterator();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (startRegion.contains(player)) {
                if (!colors.hasNext()) {
                    throw new TooManyPlayers(dac.getMessages()
                            .get("quickstart.toomanyplayers"));
                }

                Color color = colors.next();
                StartStagePlayer stagePlayer =
                        new StartStagePlayer(color, player);
                players.add(stagePlayer);
            }
        }
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public Set<StagePlayer> getPlayers() {
        return players;
    }

    @Override
    public PlayersRefSet getSpectators() {
        return new PlayersRefHashSet();
    }

    public int size() {
        return players.size();
    }
}
