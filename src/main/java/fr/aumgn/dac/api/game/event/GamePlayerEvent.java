package fr.aumgn.dac.api.game.event;

import fr.aumgn.dac.api.DACMessage;
import fr.aumgn.dac.api.exception.PlayerCastException;
import fr.aumgn.dac.api.game.Game;
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
	
	public void sendToPlayer(DACMessage message, Object... args) {
		messages.add(new PlayerMessage(player, message, args));
	}
	
	public void sendToOthers(String message) {
		messages.add(new PlayerMessage(player, message));
	}
	
	public void sendToOthers(DACMessage message, Object... args) {
		messages.add(new OthersMessage(player, message, player.getDisplayName(), args));
	}
}
