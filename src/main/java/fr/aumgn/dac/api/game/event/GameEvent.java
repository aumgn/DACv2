package fr.aumgn.dac.api.game.event;

import java.util.ArrayList;
import java.util.List;

import fr.aumgn.dac.api.DACMessage;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.exception.PlayerCastException;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.messages.GameMessage;
import fr.aumgn.dac.api.game.messages.GeneralMessage;
import fr.aumgn.dac.api.stage.StagePlayer;

public abstract class GameEvent {

    private Game game;
    protected List<GameMessage> messages;

    public GameEvent(Game game) {
        this.game = game;
        this.messages = new ArrayList<GameMessage>();
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

    public void sendMessages() {
        for (GameMessage message : messages) {
            message.send();
        }
    }

    public void send(String message) {
        messages.add(new GeneralMessage(game, message));
    }

    public void send(DACMessage message, Object... args) {
        messages.add(new GeneralMessage(game, message, args));
    }

}
