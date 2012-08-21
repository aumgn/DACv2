package fr.aumgn.dac2.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.dac2.arena.Arena;

public class GameListener implements Listener {

    private final Game game;
    private final Arena arena;

    public GameListener(Game game) {
        this.game = game;
        this.arena = game.getArena();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!game.isPlayerTurn(player)) {
            return;
        }

        if (!(arena.isIn(player.getWorld())
                && arena.getPool().contains(new Vector(player)))) {
            return;
        }

        game.onJumpSuccess(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamage(EntityDamageEvent event) {
        if (event.getCause() != DamageCause.FALL) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (!game.isPlayerTurn(player)) {
            return;
        }

        if (!(arena.isIn(player.getWorld()) && arena.getSurroundingRegion()
                .contains(new Vector(player)))) {
            return;
        }

        game.onJumpFail(player);
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRawQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!game.isPlayerTurn(player)) {
            return;
        }

        game.onQuit(player);
    }
}
