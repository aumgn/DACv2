package fr.aumgn.dac.game;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.stage.Stage;

public interface Game extends Stage {
	
	GameMode getMode();
	
	void nextTurn();
	
	boolean isPlayerTurn(DACPlayer player);
	
	void onFallDamage(EntityDamageEvent event);
	
	void onMove(PlayerMoveEvent event);
	
	void onQuit(PlayerQuitEvent event);
	
}