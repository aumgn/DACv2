package fr.aumgn.dac.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.stage.Stage;

public class DACListener implements Listener {
	
	private Game getGame(Player player) {
		Stage stage = DAC.getStageManager().get(player);
		if (!(stage instanceof Game)) {
			return null;
		}
		return (Game) stage;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onDamage(EntityDamageEvent event) {
		DamageCause cause = event.getCause();
		if (event.getEntity() instanceof Player && cause == DamageCause.FALL) {
			Player player = (Player)event.getEntity();
			Game game = getGame(player);
			
			if (game == null) {
				return;
			}
			
			if (!game.getArena().getPool().isAbove(player)) {
				return;
			}
			
			game.onFallDamage(event);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMove(PlayerMoveEvent event) {
		Game game = getGame(event.getPlayer());
		if (game == null) {
			return;
		}
		
		game.onMove(event);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent event) {
		DACPlayer player = DAC.getPlayerManager().get(event.getPlayer()); 
		if (player == null) {
			return;
		}
		
		player.getStage().removePlayer(player);
	}

}
