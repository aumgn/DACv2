package fr.aumgn.dac.api.game;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.dac.api.stage.StagePlayer;

public interface Game<T extends StagePlayer> extends Stage<T> {
	
	GameMode<T> getMode();
	
	void nextTurn();
	
	boolean isPlayerTurn(T player);
	
	void onFallDamage(EntityDamageEvent event);
	
	void onMove(PlayerMoveEvent event);

	GameOptions getOptions();
	
	boolean addSpectator(Player player);
	
	boolean removeSpectator(Player player);
	
}