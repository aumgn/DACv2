package fr.aumgn.dac.game.mode.classic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACUtil;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.arenas.Pool;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.mode.SimpleGameModeHandler;
import fr.aumgn.dac.player.DACPlayer;

public class ClassicGameModeHandler extends SimpleGameModeHandler {
	
	private Game game;
	private DACArena arena;
	private List<String> lostOrder;
	private Map<ClassicGamePlayer, Vector> playersWhoLostLastTurn;
	
	public ClassicGameModeHandler(Game game) {
		this.game = game;
		this.arena = game.getArena();
		this.lostOrder = new ArrayList<String>();
		this.playersWhoLostLastTurn = new LinkedHashMap<ClassicGamePlayer, Vector>();
	}
	
	@Override
	public void onStart() {
		if (DAC.getConfig().getResetOnStart()) {
			arena.getPool().reset();
		}
		game.send(DACMessage.GameStart);
		game.send(DACMessage.GamePlayers);
		int i = 1;
		for (DACPlayer player : game.getPlayers()) {
			game.send(DACMessage.GamePlayerList.format(i, player.getDisplayName()));
			i++;
		}
		game.send(DACMessage.GameEnjoy);
	}
	
	@Override	
	public void onNewTurn() {
		for (Entry<ClassicGamePlayer, Vector> entry : playersWhoLostLastTurn.entrySet()) {
			lostOrder.add(entry.getKey().getDisplayName());
			arena.getPool().rip(entry.getValue(), entry.getKey().getDisplayName());
		}
	}
	
	@Override
	public void onTurn(DACPlayer player) {
		game.send(DACMessage.GamePlayerTurn.format(player.getDisplayName()), player);
		player.tpToDiving();
	}

	@Override
	public void onSuccess(DACPlayer dacPlayer) {
		ClassicGamePlayer player = (ClassicGamePlayer)dacPlayer;
		game.send(DACMessage.GameJumpSuccess.format(player.getDisplayName()), player);
		Location loc = player.getPlayer().getLocation();
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		Pool pool = arena.getPool();
		boolean dac = pool.isADACPattern(x, z);
		if (player.mustConfirmate()) {
			if (dac) {
				game.send(DACMessage.GameDACConfirmation.format(dacPlayer.getDisplayName()));
				game.send(DACMessage.GameDACConfirmation2);
				pool.putDACColumn(x, z, dacPlayer.getColor());
			} else {
				game.send(DACMessage.GameConfirmation.format(dacPlayer.getDisplayName()));
				pool.putColumn(x, z, dacPlayer.getColor());
			}
			onPlayerWin(player);
		} else {
			if (dac) {
				game.send(DACMessage.GameDAC.format(dacPlayer.getDisplayName()));
				player.winLive();
				game.send(DACMessage.GameLivesAfterDAC.format(player.getLives()));
			} else {
				game.send(DACMessage.GameJumpSuccess.format(dacPlayer.getDisplayName()));
			}
			if (dac) {
				pool.putDACColumn(x, z, dacPlayer.getColor());
			} else {
				pool.putColumn(x, z, dacPlayer.getColor());
			}
		}
		
		player.tpToStart();
		game.nextTurn();
	}

	@Override
	public void onFail(DACPlayer dacPlayer) {
		ClassicGamePlayer player = (ClassicGamePlayer)dacPlayer;
		
		game.send(DACMessage.GameJumpFail.format(player.getDisplayName()), player);

		if (player.mustConfirmate()) {
			game.send(DACMessage.GameConfirmationFail);
			for (ClassicGamePlayer playerWhoLost : playersWhoLostLastTurn.keySet()) {
				playerWhoLost.resetLives();
			}
			playersWhoLostLastTurn = new LinkedHashMap<ClassicGamePlayer, Vector>();
			player.setMustConfirmate(false);
		} else {
			player.looseLive();
			if (player.hasLost()) {
				Vector vec = DACUtil.getDeathBlockVector(player.getPlayer().getLocation());
				playersWhoLostLastTurn.put(player, vec);
				onPlayerLoss(player, false);
			} else {
				game.send(DACMessage.GameLivesAfterFail.format(player.getLives()));
			}
		}
		
		player.tpToStart();
		game.nextTurn();
	}
	
	@Override
	public void onQuit(DACPlayer dacPlayer) {
		ClassicGamePlayer player = (ClassicGamePlayer)dacPlayer;
		player.looseAllLives();
		onPlayerLoss(player, true);
	}
	
	private ClassicGamePlayer getLastPlayer() {
		int i = 0;
		ClassicGamePlayer playerLeft = null;
		for (DACPlayer player : game.getPlayers()) {
			ClassicGamePlayer gamePlayer = (ClassicGamePlayer)player;
			if (!gamePlayer.hasLost()) {
				playerLeft = gamePlayer;
				i++;
			}
		}
		return (i == 1) ? playerLeft : null;
	}	
	
	public void onPlayerLoss(ClassicGamePlayer player, boolean force) {
		ClassicGamePlayer lastPlayer = getLastPlayer();
		if (lastPlayer != null) {
			if (!force && lastPlayer.getIndex() > player.getIndex() && lastPlayer.getLives() == 0) {
				lastPlayer.setMustConfirmate(true);
				game.send(DACMessage.GameMustConfirmate.format(lastPlayer.getDisplayName()));
				game.nextTurn();
			} else {
				onPlayerWin(lastPlayer);
			}
		} else {
			if (game.isPlayerTurn(player)) {
				game.nextTurn(); 
			}
		}		
	}
	
	public void onPlayerWin(ClassicGamePlayer player) {
		game.send(DACMessage.GameFinished);
		
		for (Entry<ClassicGamePlayer, Vector> entry : playersWhoLostLastTurn.entrySet()) {
			ClassicGamePlayer dacPlayer = entry.getKey();
			arena.getPool().rip(entry.getValue(), dacPlayer.getDisplayName());
			lostOrder.add(dacPlayer.getDisplayName());
		}

		game.send(DACMessage.GameWinner.format(player.getDisplayName()));

		int i=2;
		for (int index = lostOrder.size()-1 ; index >= 0; index--) {
			String name = lostOrder.get(index);
			game.send(DACMessage.GameRank.format(i, name));
			i++;
		}
		
		game.stop();
	}
	
	

}
