package fr.aumgn.dac.stage;

import java.util.List;

import org.bukkit.entity.Player;

import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.arenas.DACArena;

public interface Stage {
	
	DACArena getArena();

	boolean containsPlayer(Player player);
	
	boolean containsPlayer(DACPlayer player);
	
	void removePlayer(DACPlayer player);
	
	List<DACPlayer> getPlayers();
	
	void stop();
	
}
