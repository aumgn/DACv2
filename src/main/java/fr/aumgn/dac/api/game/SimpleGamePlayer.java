package fr.aumgn.dac.api.game;

import org.bukkit.Bukkit;
import org.bukkit.util.Vector;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.stage.SimpleStagePlayer;
import fr.aumgn.dac.api.stage.StagePlayer;

public class SimpleGamePlayer extends SimpleStagePlayer {

    private int index;
    private Vector propulsion;
    private int propulsionDelay;

    public SimpleGamePlayer(Game game, StagePlayer player, int index) {
        super(player.getPlayer(), game, player.getColor(), player.getStartLocation());
        this.index = index;
        propulsion = game.getOptions().getPropulsion();
        propulsionDelay = game.getOptions().getPropulsionDelay();
    }

    @Override
    public String formatForList() {
        return " " + index + super.formatForList();
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void tpToDiving() {
        super.tpToDiving();
        if (propulsionDelay == 0) {
            propulse();
        } else {
            Bukkit.getScheduler().scheduleAsyncDelayedTask(DAC.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    propulse();
                }
            }, propulsionDelay);
        }
    }

    private void propulse() {
        getPlayer().setVelocity(propulsion);
    }

}
