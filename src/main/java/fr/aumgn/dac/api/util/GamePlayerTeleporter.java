package fr.aumgn.dac.api.util;

import org.bukkit.Bukkit;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.config.DACConfig;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StagePlayer;

public class GamePlayerTeleporter extends PlayerTeleporter {

    private Game game;
    
    public GamePlayerTeleporter(StagePlayer player) {
        super(player);
        game = (Game) player.getStage();
    }
    
    @Override
    public void toDiving() {
        super.toDiving();
        int propulsionDelay = game.getOptions().getPropulsionDelay();
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
        player.getPlayer().setVelocity(game.getOptions().getPropulsion());
    }
    
    public void afterFail() {
        DACConfig config = DAC.getConfig();
        if (game.getPlayers().size() > 1 && config.getTpAfterFail()) {
            int delay = config.getTpAfterFailDelay();
            if (delay == 0) {
                toStart();
            } else {
                Bukkit.getScheduler().scheduleAsyncDelayedTask(DAC.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        toStart();
                    }
                }, delay);
            }
        }
    }

}
