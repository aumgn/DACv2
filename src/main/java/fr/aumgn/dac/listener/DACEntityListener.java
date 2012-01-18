package fr.aumgn.dac.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;

public class DACEntityListener extends EntityListener {

	public void onEntityDamage(EntityDamageEvent event) {
		DamageCause cause = event.getCause();
		if (event.getEntity() instanceof Player && cause == DamageCause.FALL) {
			DACGame game = DAC.getGame((Player)event.getEntity());
			if (game != null) { game.onPlayerDamage(event); }
		}
	}

}
