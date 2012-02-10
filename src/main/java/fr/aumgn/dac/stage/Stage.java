package fr.aumgn.dac.stage;

import java.util.List;

import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.arenas.DACArena;

public interface Stage {
	
	DACArena getArena();
	
	void removePlayer(DACPlayer player);
	
	List<DACPlayer> getPlayers();
	
	void registerAll();
	
	void unregisterAll();
	
	void send(Object message);
	
	void send(Object message, DACPlayer exclude);
	
	void stop();
	
}
