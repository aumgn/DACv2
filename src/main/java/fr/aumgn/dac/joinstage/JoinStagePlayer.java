package fr.aumgn.dac.joinstage;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.dac.config.DACColor;
import fr.aumgn.dac.player.DACSimplePlayer;
import fr.aumgn.dac.stage.Stage;

public class JoinStagePlayer extends DACSimplePlayer {

	public JoinStagePlayer(Stage stage, Player player, DACColor color, Location startLocation) {
		super(player, stage, color, startLocation);
	}
	
}
