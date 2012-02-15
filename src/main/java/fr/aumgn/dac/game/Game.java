package fr.aumgn.dac.game;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aumgn.dac.game.mode.GameMode;
import fr.aumgn.dac.game.options.GameOptions;
import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.stage.Stage;

public interface Game<T extends DACPlayer> extends Stage<T> {
	
	GameMode<T> getMode();
	
	void nextTurn();
	
	boolean isPlayerTurn(T player);
	
	void onFallDamage(EntityDamageEvent event);
	
	void onMove(PlayerMoveEvent event);

	GameOptions getOptions();
	
}