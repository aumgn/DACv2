package fr.aumgn.dac.stage;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.player.DACPlayer;

public class StageManager {

	private Map<DACArena, Stage> stages;
	private Map<Player, DACPlayer> players;
	
	public StageManager() {
		stages = new HashMap<DACArena, Stage>();
	}
	
	public boolean hasStage(DACArena arena) {
		return stages.containsKey(arena);
	}
	
	public Stage get(DACArena arena) {
		return stages.get(arena);
	}
	
	public Stage get(Player player) {
		DACPlayer dacPlayer = getPlayer(player);
		if (dacPlayer == null) {
			return null;
		}
		return dacPlayer.getStage();
	}
	
	public DACPlayer getPlayer(Player player) {
		return players.get(player);
	}
	
	public void registerPlayer(DACPlayer player) {
		if (players.containsKey(player.getPlayer())) {
			throw new RuntimeException();
		}
		players.put(player.getPlayer(), player);
	}
	
}
