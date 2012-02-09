package fr.aumgn.dac.game.mode.default_;

import org.bukkit.Location;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.arenas.Pool;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.mode.SimpleGameModeHandler;
import fr.aumgn.dac.player.DACPlayer;

public class DefaultGameModeHandler extends SimpleGameModeHandler {
	
	private Game game;
	private DACArena arena;
	
	public DefaultGameModeHandler(Game game) {
		this.game = game;
		this.arena = game.getArena();
	}
	
	@Override
	public void onStart() {
		if (DAC.getConfig().getResetOnStart()) {
			arena.getPool().reset();
		}
		game.send(DACMessage.GameStart);
		game.send(DACMessage.GamePlayers);
		int i = 0;
		for (DACPlayer player : game.getPlayers()) {
			game.send(DACMessage.GamePlayerList.format(i, player.getDisplayName()));
			i++;
		}
		game.send(DACMessage.GameEnjoy);		
	}
	
	@Override
	public void onTurn(DACPlayer player) {
		game.send(DACMessage.GamePlayerTurn.format(player.getDisplayName()), player);
		player.tpToDiving();
	}

	@Override
	public void onSuccess(DACPlayer player) {
		game.send(DACMessage.GameJumpSuccess.format(player.getDisplayName()), player);
		Location loc = player.getPlayer().getLocation();
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		player.tpToStart();
		Pool pool = arena.getPool(); 
		if (pool.isADACPattern(x, z)) {
			pool.putDACColumn(x, z, player.getColor());
		} else {
			pool.putColumn(x, z, player.getColor());
		}
		game.nextTurn();
	}

	@Override
	public void onFail(DACPlayer player) {
		game.send(DACMessage.GameJumpFail.format(player.getDisplayName()), player);
		player.tpToStart();
		game.nextTurn();
	}

}
