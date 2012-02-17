package fr.aumgn.dac.api.joinstage;

import org.bukkit.entity.Player;

import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;

public interface JoinStage<T extends StagePlayer> extends Stage<T> {

	void addPlayer(Player player, String[] args);
	
	boolean isMinReached(GameMode<?> mode);
	
	boolean isMaxReached();
	
}
