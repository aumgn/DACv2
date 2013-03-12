package fr.aumgn.dac2.game.classic;

import java.util.Set;

import fr.aumgn.bukkitutils.playerref.set.PlayersRefSet;
import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.stage.StagePlayer;

public class ToSuddenDeath implements GameStartData {

    private final Arena arena;
    private final Set<ClassicGamePlayer> players;
    private final PlayersRefSet spectators;

    public ToSuddenDeath(Arena arena, Set<ClassicGamePlayer> players,
            PlayersRefSet spectators) {
        this.arena = arena;
        this.players = players;
        this.spectators = spectators;
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public Set<? extends StagePlayer> getPlayers() {
        return players;
    }

    @Override
    public PlayersRefSet getSpectators() {
        return spectators;
    }
}
