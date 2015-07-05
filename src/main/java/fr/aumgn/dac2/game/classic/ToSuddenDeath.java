package fr.aumgn.dac2.game.classic;

import fr.aumgn.dac2.arena.Arena;
import fr.aumgn.dac2.game.start.GameStartData;
import fr.aumgn.dac2.stage.Spectators;
import fr.aumgn.dac2.stage.StagePlayer;

import java.util.Set;

public class ToSuddenDeath implements GameStartData {

    private final Arena arena;
    private final Set<ClassicGamePlayer> players;
    private final Spectators spectators;

    public ToSuddenDeath(Arena arena, Set<ClassicGamePlayer> players,
                         Spectators spectators) {
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
    public Spectators getSpectators() {
        return spectators;
    }
}
