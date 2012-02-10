package fr.aumgn.dac.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.dac.config.DACColor;
import fr.aumgn.dac.stage.Stage;

public interface DACPlayer {
	
	Stage getStage();

	Player getPlayer();
	
	String getDisplayName();
	
	DACColor getColor();
	
	Location getStartLocation();

	void send(Object message);
	
	void sendToOthers(Object message);
	
	void tpToStart();

	void tpToDiving();

}
