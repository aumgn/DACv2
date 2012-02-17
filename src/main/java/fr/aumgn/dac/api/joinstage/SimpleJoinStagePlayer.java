package fr.aumgn.dac.api.joinstage;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.dac.api.config.DACColor;
import fr.aumgn.dac.api.stage.SimpleStagePlayer;
import fr.aumgn.dac.api.stage.Stage;

public class SimpleJoinStagePlayer extends SimpleStagePlayer {

    public SimpleJoinStagePlayer(Stage<SimpleJoinStagePlayer> stage, Player player, DACColor color, Location startLocation) {
        super(player, stage, color, startLocation);
    }

}
