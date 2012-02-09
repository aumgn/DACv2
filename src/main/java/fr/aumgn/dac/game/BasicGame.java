package fr.aumgn.dac.game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.game.mode.GameMode;
//import fr.aumgn.dac.game.mode.GameModeHandler;
import fr.aumgn.dac.joinstep.JoinStage;
import fr.aumgn.dac.player.DACPlayer;

public class BasicGame implements Game {

	private DACArena arena;
	//private GameModeHandler modeHandler;
	private DACPlayer[] players;
	
	public BasicGame(GameMode gameMode, JoinStage joinStage) {
		this.arena = joinStage.getArena();
		List<DACPlayer> roulette = joinStage.getPlayers();
		players = new DACPlayer[roulette.size()];
		//modeHandler = gameMode.createHandler(this);
		Random rand = new Random();
		for (int i=0; i< players.length; i++) {
			int j = rand.nextInt(roulette.size());
			players[i] = gameMode.createPlayer(roulette.remove(j));
		}
	}
	
	@Override
	public DACArena getArena() {
		return arena;
	}

	@Override
	public boolean containsPlayer(DACPlayer player) {
		return player.getStage() == this;
	}
	
	@Override
	public void removePlayer(DACPlayer player) {
		// TODO Auto-generated method stub
	}


	@Override
	public List<DACPlayer> getPlayers() {
		return Arrays.asList(players);
	}

	@Override
	public void stop() {
	}

	@Override
	public boolean containsPlayer(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

}
