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
import fr.aumgn.dac.area.column.GlassColumn;
import fr.aumgn.dac.area.column.SimpleColumn;
import fr.aumgn.dac.arena.DACArena;
import fr.aumgn.dac.arena.Pool;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.event.classic.DACClassicDACEvent;
import fr.aumgn.dac.event.classic.DACClassicLooseEvent;
import fr.aumgn.dac.event.classic.DACClassicWinEvent;
import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.game.mode.SimpleGameModeHandler;
import fr.aumgn.dac.game.options.GameOptions;
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
		GameOptions options = game.getOptions();
		String livesOption = options.get("lives", "0");
		int lives = Integer.parseInt(livesOption);
		for (DACPlayer dacPlayer : game.getPlayers()) {
			ClassicGamePlayer player = (ClassicGamePlayer)dacPlayer;
			player.setLives(lives);
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
		playersWhoLostLastTurn = new LinkedHashMap<ClassicGamePlayer, Vector>();
	}
	
	@Override
	public void onTurn(DACPlayer dacPlayer) {
		ClassicGamePlayer player = (ClassicGamePlayer)dacPlayer;
		if (player.hasLost()) {
			game.nextTurn();
		} else {
			player.send(DACMessage.GamePlayerTurn2.getValue());
			player.sendToOthers(DACMessage.GamePlayerTurn.format(player.getDisplayName()));
			player.tpToDiving();
		}
	}

	@Override
	public void onSuccess(DACPlayer dacPlayer) {
		ClassicGamePlayer player = (ClassicGamePlayer)dacPlayer;
		Location loc = player.getPlayer().getLocation();
		int x = loc.getBlockX();
		int z = loc.getBlockZ();
		Pool pool = arena.getPool();
		boolean dac = pool.isADACPattern(x, z);

		player.tpToStart();
		if (dac) {
			DAC.callEvent(new DACClassicDACEvent(game, player));
			pool.putColumn(new GlassColumn(), dacPlayer.getColor(), x, z);
		} else {
			pool.putColumn(new SimpleColumn(), dacPlayer.getColor(), x, z);
		}
		
		if (player.mustConfirmate()) {
			if (dac) {
				player.send(DACMessage.GameDACConfirmation3);
				player.sendToOthers(DACMessage.GameDACConfirmation.format(dacPlayer.getDisplayName()));
				game.send(DACMessage.GameDACConfirmation2);
			} else {
				player.send(DACMessage.GameConfirmation2);
				player.sendToOthers(DACMessage.GameConfirmation.format(dacPlayer.getDisplayName()));
			}
			onPlayerWin(player);
		} else {
			if (dac) {
				player.winLive();
				player.send(DACMessage.GameDAC2);
				player.sendToOthers(DACMessage.GameDAC.format(dacPlayer.getDisplayName()));
				player.send(DACMessage.GameLivesAfterDAC2.format(player.getLives()));
				player.sendToOthers(DACMessage.GameLivesAfterDAC.format(player.getLives()));
			} else {
				player.send(DACMessage.GameJumpSuccess2);
				player.sendToOthers(DACMessage.GameJumpSuccess.format(dacPlayer.getDisplayName()));
			}
			game.nextTurn();
		}
	}

	@Override
	public void onFail(DACPlayer dacPlayer) {
		ClassicGamePlayer player = (ClassicGamePlayer)dacPlayer;
		
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
		
		DAC.callEvent(new DACClassicLooseEvent(game, player));

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
		DAC.callEvent(new DACClassicWinEvent(game, player));
		
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
