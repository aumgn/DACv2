package fr.aumgn.dac.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.DACJoinStep;

public class DACPlayerListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onDamage(EntityDamageEvent event) {
		DamageCause cause = event.getCause();
		if (event.getEntity() instanceof Player && cause == DamageCause.FALL) {
			DACGame game = DAC.getGame((Player)event.getEntity());
			if (game != null) { game.onPlayerDamage(event); }
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMove(PlayerMoveEvent event) {
		DACGame game = DAC.getGame(event.getPlayer());
		if (game != null) { game.onPlayerMove(event); }
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		DACJoinStep joinStep = DAC.getJoinStep(player);
		if (joinStep != null) {
			joinStep.remove(player);
			return;
		}
		DACGame game = DAC.getGame(player);
		if (game != null) { game.onPlayerQuit(player); }
	}

}
