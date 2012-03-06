package fr.aumgn.dac.api.game.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.exception.PlayerCastException;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.messages.GameMessageContent;
import fr.aumgn.dac.api.game.messages.GameMessage;
import fr.aumgn.dac.api.game.messages.GeneralMessage;
import fr.aumgn.dac.api.stage.StagePlayer;

public abstract class GameEvent {

    private Game game;
    protected List<GameMessage> messages;
    private Set<StagePlayer> losses;
    private StagePlayer winner;

    public GameEvent(Game game) {
        this.game = game;
        this.messages = new ArrayList<GameMessage>();
        this.losses = new LinkedHashSet<StagePlayer>();
        this.winner = null;
    }

    public Game getGame() {
        return game;
    }

    public <T extends StagePlayer> Iterable<T> getPlayers(Class<T> cls) {
        ArrayList<T> list = new ArrayList<T>();
        for (StagePlayer player : game.getPlayers()) {
            if (player.getClass().isAssignableFrom(cls)) {
                list.add(cls.cast(player));
            } else {
                throw new PlayerCastException();
            }
        }
        return list;
    }

    public Arena getArena() {
        return game.getArena();
    }

    public Iterable<GameMessage> messages() {
        return Collections.unmodifiableCollection(messages);
    }

    public Iterable<StagePlayer> losses() {
        return Collections.unmodifiableCollection(losses);
    }

    public StagePlayer getWinner() {
        return winner;
    }

    public void send(String message) {
        messages.add(new GeneralMessage(game, message));
    }

    public void send(GameMessageContent message, Object... args) {
        messages.add(new GeneralMessage(game, message, args));
    }

    public void addLoss(StagePlayer player) {
        losses.add(player);
    }

    public void setWinner(StagePlayer winner) {
        this.winner = winner;
    }

}
