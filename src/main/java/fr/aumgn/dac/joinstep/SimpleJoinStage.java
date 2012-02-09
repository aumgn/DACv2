package fr.aumgn.dac.joinstep;

import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.player.DACPlayer;

public class SimpleJoinStage implements JoinStage {
	
	private DACArena arena;
	private List<DACPlayer> players;

	public SimpleJoinStage(DACArena arena) {
		
	}

	@Override
	public DACArena getArena() {
		return arena;
	}

	@Override
	public void addPlayer(Player player, String[] colors) {
	}

	@Override
	public void removePlayer(DACPlayer player) {
	}
	
	@Override
	public boolean containsPlayer(Player player) {
		DACPlayer dacPlayer = DAC.getStageManager().getPlayer(player);
		return containsPlayer(dacPlayer);
	}

	@Override
	public boolean containsPlayer(DACPlayer player) {
		return player.getStage() == this;
	}

	@Override
	public List<DACPlayer> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	@Override
	public void stop() {
	}

	@Override
	public boolean isMinReached() {
		return players.size() > 1;
	}

	@Override
	public boolean isMaxReached() {
		return (players.size() >= DAC.getConfig().getMaxPlayers());
	}

}
