package fr.aumgn.dac.plugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.StagePlayer;

public class DACListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent event) {
        DamageCause cause = event.getCause();
        if (event.getEntity() instanceof Player && cause == DamageCause.FALL) {
            Player player = (Player) event.getEntity();
            Game<?> game = DAC.getStageManager().getGame(player);
            if (game == null) {
                return;
            }

            game.onFallDamage(event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        Game<?> game = DAC.getStageManager().getGame(event.getPlayer());
        if (game == null) {
            return;
        }

        game.onMove(event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        StagePlayer player = DAC.getPlayerManager().get(event.getPlayer());
        if (player == null) {
            return;
        }

        player.getStage().removePlayer(player);
    }

}
