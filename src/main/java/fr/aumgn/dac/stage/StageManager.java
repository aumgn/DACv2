package fr.aumgn.dac.stage;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.player.DACPlayer;

public class StageManager {

	private Map<DACArena, Stage> stages;
	
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
		DACPlayer dacPlayer = DAC.getPlayerManager().get(player);
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
	
}
