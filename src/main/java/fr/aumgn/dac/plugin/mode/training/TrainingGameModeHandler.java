package fr.aumgn.dac.plugin.mode.training;

import org.bukkit.Location;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.area.column.GlassColumn;
import fr.aumgn.dac.api.area.column.SimpleColumn;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.arena.Pool;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.mode.SimpleGameModeHandler;

public class TrainingGameModeHandler extends SimpleGameModeHandler<TrainingGamePlayer> {

	private Game<TrainingGamePlayer> game;
	private Arena arena;

	public TrainingGameModeHandler(Game<TrainingGamePlayer> game) {
		this.game = game;
		this.arena = game.getArena();
	}
	
	@Override
	public void onStart() {
		if (DAC.getConfig().getResetOnStart()) {
			arena.getPool().reset();
		}
		game.send(DACMessage.GameStart);
	}

	@Override
	public void onTurn(TrainingGamePlayer player) {
		if (!player.isPlaying()) {
			game.nextTurn();
		} else {
			game.send(DACMessage.GamePlayerTurn.format(player.getDisplayName()), player);
			player.tpToDiving();
		}
	}

	@Override
	public void onSuccess(TrainingGamePlayer player) {
		game.send(DACMessage.GameJumpSuccess.format(player.getDisplayName()), player);
		Location loc = player.getPlayer().getLocation();
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		player.tpToStart();
		Pool pool = arena.getPool(); 
		if (pool.isADACPattern(x, z)) {
			player.incrementDACs();
			pool.setColumn(new GlassColumn(), player.getColor(), x, z);
		} else {
			player.incrementSuccesses();
			pool.setColumn(new SimpleColumn(), player.getColor(), x, z);
		}
		game.nextTurn();
	}

	@Override
	public void onFail(TrainingGamePlayer player) {
		game.send(DACMessage.GameJumpFail.format(player.getDisplayName()), player);
		player.tpToStart();
		player.incrementFails();
		game.nextTurn();	
	}
	
	@Override
	public void onQuit(TrainingGamePlayer player) {
		player.setPlaying(false);
		int count = 0;
		for (TrainingGamePlayer gamePlayer : game.getPlayers()) {
			player = (TrainingGamePlayer)gamePlayer;
			if (player.isPlaying()) {
				count++;
			}
		}
		if (count == 0) {
			game.stop();
		}
	}
	
	@Override
	public void onStop() {
		for (TrainingGamePlayer player : game.getPlayers()) {
			player.send("Reussi : " + player.getSuccesses());
			player.send("Dés a coudre : " + player.getDACs());
			player.send("Manqués : " + player.getFails());
		}
	}

}
