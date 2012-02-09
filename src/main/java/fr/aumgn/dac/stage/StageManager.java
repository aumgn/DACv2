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
		players = new HashMap<Player, DACPlayer>();
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
	
	public void register(Stage stage) {
		if (stages.containsKey(stage.getArena())) {
			throw new RuntimeException();
		}
		stages.put(stage.getArena(), stage);
	}

	public void unregister(Stage stage) {
		if (!stages.containsKey(stage.getArena())) {
			throw new RuntimeException();
		}
		stages.remove(stage.getArena());
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

	public void unregisterPlayer(DACPlayer player) {
		if (!players.containsKey(player.getPlayer())) {
			throw new RuntimeException();
		}
		players.remove(player.getPlayer());
	}

}
