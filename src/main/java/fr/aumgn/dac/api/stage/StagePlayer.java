package fr.aumgn.dac.api.stage;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.dac.api.config.DACColor;

public interface StagePlayer {
	
	Stage<?> getStage();

	Player getPlayer();
	
	String getDisplayName();
	
	DACColor getColor();
	
	Location getStartLocation();

	void send(Object message);
	
	void sendToOthers(Object message);
	
	String formatForList();

	void tpToStart();

	void tpAfterJump();
	
	void tpAfterFail();

	void tpToDiving();
	
}
