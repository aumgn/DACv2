package fr.aumgn.dac.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.DACJoinStep;

public class DACPlayerListener extends PlayerListener {
	
	public void onPlayerMove(PlayerMoveEvent event) {
		DACGame game = DAC.getGame(event.getPlayer());
		if (game != null) { game.onPlayerMove(event); }
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
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
