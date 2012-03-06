package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.exception.PlayerCastException;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.messages.GameMessageContent;
import fr.aumgn.dac.api.game.messages.OthersMessage;
import fr.aumgn.dac.api.game.messages.PlayerMessage;
import fr.aumgn.dac.api.stage.StagePlayer;

public abstract class GamePlayerEvent extends GameEvent {

    protected StagePlayer player;

    public GamePlayerEvent(StagePlayer player) {
        super((Game) player.getStage());
        this.player = player;
    }

    public StagePlayer getPlayer() {
        return player;
    }

    public <T extends StagePlayer> T getPlayer(Class<T> cls) {
        if (player.getClass().isAssignableFrom(cls)) {
            return cls.cast(player);
        } else {
            throw new PlayerCastException();
        }
    }

    public void sendToPlayer(String message) {
        messages.add(new PlayerMessage(player, message));
    }

    public void sendToPlayer(GameMessageContent message, Object... args) {
        messages.add(new PlayerMessage(player, message, args));
    }

    public void sendToOthers(String message) {
        messages.add(new OthersMessage(game, player, message));
    }

    public void sendToOthers(GameMessageContent message, Object... args) {
        Object[] argsWithPlayer = new Object[args.length + 1];
        argsWithPlayer[0] = player.getDisplayName();
        System.arraycopy(args, 0, argsWithPlayer, 1, args.length);
        messages.add(new OthersMessage(game, player, message, argsWithPlayer));
    }
    
    public void setLoss() {
        super.addLoss(player);
    }
    
    public void setWin() {
        super.setWinner(player);
    }
    
}
