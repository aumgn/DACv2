package fr.aumgn.dac.api.stage;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class StagePlayerManager {

	private Map<Player, StagePlayer> players;
	
	public StagePlayerManager() {
		players = new HashMap<Player, StagePlayer>();
	}

	public StagePlayer get(Player player) {
		return players.get(player);
	}
	
	public void register(StagePlayer player) {
		if (players.containsKey(player.getPlayer())) {
			throw new RuntimeException();
		}
		players.put(player.getPlayer(), player);
	}

	public void unregister(StagePlayer player) {
		if (!players.containsKey(player.getPlayer())) {
			throw new RuntimeException();
		}
		players.remove(player.getPlayer());
	}
	
}
