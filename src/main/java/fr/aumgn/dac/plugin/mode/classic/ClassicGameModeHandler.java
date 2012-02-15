package fr.aumgn.dac.plugin.mode.classic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.arena.Pool;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.event.game.DACGameDACEvent;
import fr.aumgn.dac.api.event.game.DACGameLooseEvent;
import fr.aumgn.dac.api.event.game.DACGameWinEvent;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.GameOptions;
import fr.aumgn.dac.api.game.mode.SimpleGameModeHandler;
import fr.aumgn.dac.api.util.DACUtil;
import fr.aumgn.dac.plugin.area.column.GlassColumn;
import fr.aumgn.dac.plugin.area.column.SimpleColumn;

public class ClassicGameModeHandler extends SimpleGameModeHandler<ClassicGamePlayer> {
	
	private Game<ClassicGamePlayer> game;
	private Arena arena;
	private List<String> lostOrder;
	private Map<ClassicGamePlayer, Vector> playersWhoLostLastTurn;
	
	public ClassicGameModeHandler(Game<ClassicGamePlayer> game) {
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
		GameOptions options = game.getOptions();
		String livesOption = options.get("lives", "0");
		int lives = Integer.parseInt(livesOption);
		for (ClassicGamePlayer player : game.getPlayers()) {
			player.setLives(lives);
		}
		game.send(DACMessage.GameStart);
		game.send(DACMessage.GamePlayers);
		int i = 1;
		for (ClassicGamePlayer player : game.getPlayers()) {
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
		playersWhoLostLastTurn = new LinkedHashMap<ClassicGamePlayer, Vector>();
	}
	
	@Override
	public void onTurn(ClassicGamePlayer player) {
		if (player.hasLost()) {
			game.nextTurn();
		} else {
			player.send(DACMessage.GamePlayerTurn2.getValue());
			player.sendToOthers(DACMessage.GamePlayerTurn.format(player.getDisplayName()));
			player.tpToDiving();
		}
	}

	@Override
	public void onSuccess(ClassicGamePlayer player) {
		Location loc = player.getPlayer().getLocation();
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		Pool pool = arena.getPool();
		boolean dac = pool.isADACPattern(x, z);

		player.tpToStart();
		if (dac) {
			DAC.callEvent(new DACGameDACEvent(game, player));
			pool.setColumn(new GlassColumn(), player.getColor(), x, z);
		} else {
			pool.setColumn(new SimpleColumn(), player.getColor(), x, z);
		}
		
		if (player.mustConfirmate()) {
			if (dac) {
				player.send(DACMessage.GameDACConfirmation3);
				player.sendToOthers(DACMessage.GameDACConfirmation.format(player.getDisplayName()));
				game.send(DACMessage.GameDACConfirmation2);
			} else {
				player.send(DACMessage.GameConfirmation2);
				player.sendToOthers(DACMessage.GameConfirmation.format(player.getDisplayName()));
			}
			onPlayerWin(player);
		} else {
			if (dac) {
				player.winLive();
				player.send(DACMessage.GameDAC2);
				player.sendToOthers(DACMessage.GameDAC.format(player.getDisplayName()));
				player.send(DACMessage.GameLivesAfterDAC2.format(player.getLives()));
				player.sendToOthers(DACMessage.GameLivesAfterDAC.format(player.getLives()));
			} else {
				player.send(DACMessage.GameJumpSuccess2);
				player.sendToOthers(DACMessage.GameJumpSuccess.format(player.getDisplayName()));
			}
			game.nextTurn();
		}
	}

	@Override
	public void onFail(ClassicGamePlayer player) {
		player.send(DACMessage.GameJumpFail2);
		player.sendToOthers(DACMessage.GameJumpFail.format(player.getDisplayName()));

		if (player.mustConfirmate()) {
			player.send(DACMessage.GameConfirmationFail2);
			player.sendToOthers(DACMessage.GameConfirmationFail);
			for (ClassicGamePlayer playerWhoLost : playersWhoLostLastTurn.keySet()) {
				playerWhoLost.resetLives();
			}
			playersWhoLostLastTurn = new LinkedHashMap<ClassicGamePlayer, Vector>();
			player.setMustConfirmate(false);
			game.nextTurn();
		} else {
			player.looseLive();
			if (player.hasLost()) {
				Vector vec = DACUtil.getDeathBlockVector(player.getPlayer().getLocation());
				playersWhoLostLastTurn.put(player, vec);
				onPlayerLoss(player, false);
			} else {
				player.send(DACMessage.GameLivesAfterFail2.format(player.getLives()));
				player.sendToOthers(DACMessage.GameLivesAfterFail.format(player.getLives()));
				game.nextTurn();
			}
		}
		player.tpToStart();		
	}
	
	@Override
	public void onQuit(ClassicGamePlayer player) {
		player.looseAllLives();
		onPlayerLoss(player, true);
	}
	
	private ClassicGamePlayer getLastPlayer() {
		int i = 0;
		ClassicGamePlayer playerLeft = null;
		for (ClassicGamePlayer player : game.getPlayers()) {
			if (!player.hasLost()) {
				playerLeft = player;
				i++;
			}
		}
		return (i == 1) ? playerLeft : null;
	}	
	
	public void onPlayerLoss(ClassicGamePlayer player, boolean force) {
		ClassicGamePlayer lastPlayer = getLastPlayer();
		
		DAC.callEvent(new DACGameLooseEvent(game, player));

		if (lastPlayer != null) {
			if (!force && lastPlayer.getIndex() > player.getIndex() && lastPlayer.getLives() == 0) {
				lastPlayer.setMustConfirmate(true);
				lastPlayer.send(DACMessage.GameMustConfirmate2);
				lastPlayer.sendToOthers(DACMessage.GameMustConfirmate.format(lastPlayer.getDisplayName()));
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
		DAC.callEvent(new DACGameWinEvent(game, player));
		
		game.send(DACMessage.GameFinished);
		
		for (Entry<ClassicGamePlayer, Vector> entry : playersWhoLostLastTurn.entrySet()) {
			ClassicGamePlayer dacPlayer = entry.getKey();
			arena.getPool().rip(entry.getValue(), dacPlayer.getDisplayName());
			lostOrder.add(dacPlayer.getDisplayName());
		}

		game.send(DACMessage.GameWinner.format(player.getDisplayName()));

		int i=2;
		for (int index = lostOrder.size() - 1; index >= 0; index--) {
			String name = lostOrder.get(index);
			game.send(DACMessage.GameRank.format(i, name));
			i++;
		}
		
		game.stop();
	}
	
	

}
