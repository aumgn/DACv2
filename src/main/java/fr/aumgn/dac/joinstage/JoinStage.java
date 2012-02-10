package fr.aumgn.dac.joinstage;

import org.bukkit.entity.Player;

import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.stage.Stage;

public interface JoinStage extends Stage {

	void addPlayer(Player player, String[] args);
	
	boolean isMinReached(GameMode mode);
	
	boolean isMaxReached();
	
}
