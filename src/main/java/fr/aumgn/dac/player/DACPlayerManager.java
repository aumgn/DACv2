package fr.aumgn.dac.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class DACPlayerManager {

	private Map<Player, DACPlayer> players;
	
	public DACPlayerManager() {
		players = new HashMap<Player, DACPlayer>();
	}

	public DACPlayer get(Player player) {
		return players.get(player);
	}
	
	public void register(DACPlayer player) {
		if (players.containsKey(player.getPlayer())) {
			throw new RuntimeException();
		}
		players.put(player.getPlayer(), player);
	}

	public void unregister(DACPlayer player) {
		if (!players.containsKey(player.getPlayer())) {
			throw new RuntimeException();
		}
		players.remove(player.getPlayer());
	}
	
}
