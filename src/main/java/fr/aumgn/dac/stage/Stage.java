package fr.aumgn.dac.stage;

import java.util.List;

import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.arena.DACArena;

public interface Stage<T extends DACPlayer> {
	
	DACArena getArena();
	
	void removePlayer(DACPlayer player);
	
	List<T> getPlayers();
	
	void registerAll();
	
	void unregisterAll();
	
	void send(Object message);
	
	void send(Object message, DACPlayer exclude);
	
	void stop();

}
