package fr.aumgn.dac.stage;

import java.util.List;

import fr.aumgn.dac.arena.DACArena;

public interface Stage<T extends StagePlayer> {
	
	DACArena getArena();
	
	void removePlayer(StagePlayer player);
	
	List<T> getPlayers();
	
	void registerAll();
	
	void unregisterAll();
	
	void send(Object message);
	
	void send(Object message, StagePlayer exclude);
	
	void stop();

}
