package fr.aumgn.dac;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitScheduler;

import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.arenas.Pool;
import fr.aumgn.dac.config.DACMessage;

public class DACGame {
	
	private final Runnable turnTimeOutRunnable = new Runnable() { 
		@Override
		public void run() { turnTimedOut(); }
	};
	private DACArena arena;
	private DACPlayer[] players;
	private int turn;
	private int turnTimeoutTaskId;
	private List<Integer> lostOrder;
	private Map<DACPlayer, Location> playersWhoLostLastTurn;

	public DACGame(DACJoinStep joinStep) {
		this.arena = joinStep.getArena();
		List<DACPlayer> roulette = joinStep.getPlayers();
		players = new DACPlayer[roulette.size()];
		Random rand = new Random();
		for (int i=0; i< players.length; i++) {
			int j = rand.nextInt(roulette.size());
			players[i] = roulette.remove(j);
			players[i].init(this, i);
		}
		lostOrder = new ArrayList<Integer>();
		playersWhoLostLastTurn = new LinkedHashMap<DACPlayer, Location>();
		if (DAC.getDACConfig().getResetOnStart()) {
			arena.getPool().reset();
		}
		send(DACMessage.GameStart);
		send(DACMessage.GamePlayers);
		for (DACPlayer player : players) {
			send(DACMessage.GamePlayerList.format(player.getPosition(), player.getDisplayName()));
		}
		send(DACMessage.GameEnjoy);
		turn = -1;
		nextTurn();
	}

	private void send(String message) {
		for (DACPlayer player : players) {
			player.getPlayer().sendMessage(message);
		}
	}

	private void send(DACMessage lang) {
		send(lang.getValue());
	}

	private void increaseTurn() {		
		turn++;
		if (turn == players.length) {
			turn = 0;
			for (DACPlayer dacPlayer : playersWhoLostLastTurn.keySet()) {
				lostOrder.add(dacPlayer.getIndex());
			}
			playersWhoLostLastTurn = new LinkedHashMap<DACPlayer, Location>();
		}
	}

	private void nextTurn() {
		DACPlayer player;
		BukkitScheduler scheduler = Bukkit.getScheduler();
		if (scheduler.isQueued(turnTimeoutTaskId)) {
			scheduler.cancelTask(turnTimeoutTaskId);
		}
		do {
			increaseTurn();
			player = players[turn];
		} while (player.hasLost());
		send(DACMessage.GamePlayerTurn.format(player.getDisplayName()));
		player.getPlayer().teleport(arena.getDivingBoard().getLocation());
		turnTimeoutTaskId = Bukkit.getScheduler().scheduleAsyncDelayedTask(
			DAC.getPlugin(), turnTimeOutRunnable, DAC.getDACConfig().getTurnTimeOut()
		);
	}
	
	private void turnTimedOut() {
		
		onPlayerQuit(players[turn]);
	}

	private boolean isPlayerTurn(DACPlayer player) {
		return isPlayerTurn(player.getPlayer());
	}

	private boolean isPlayerTurn(Player player) {
		return players[turn].getPlayer().equals(player);
	}

	private void tpAfterJump(DACPlayer dacPlayer) {
		if (DAC.getDACConfig().getTpAfterJump()) {
			int delay = DAC.getDACConfig().getTpAfterSuccessDelay();
			if (delay > 0) { dacPlayer.getPlayer().setNoDamageTicks(delay + 1); }
			dacPlayer.tpToStart();
		} else {
			dacPlayer.getPlayer().setNoDamageTicks(20);
		}
	}

	public DACPlayer getLastPlayer() {
		int i = 0;
		DACPlayer playerLeft = null;
		for (DACPlayer player : players) {
			if (!player.hasLost()) {
				playerLeft = player;
				i++; 
			}
		}
		return (i == 1) ? playerLeft : null;
	}

	public DACArena getArena() {
		return arena;
	}

	public boolean contains(Player player) {
		for (DACPlayer dacPlayer : players) {
			if (dacPlayer.getPlayer().equals(player)) {
				return true;
			}
		}
		return false;
	}

	public DACPlayer wrapPlayer(Player player) {
		for (DACPlayer dacPlayer : players) {
			if (dacPlayer.getPlayer().equals(player)) {
				return dacPlayer;
			}
		}
		return null;
	}

	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		DACPlayer dacPlayer = wrapPlayer(player); 
		Pool pool = arena.getPool();
		if (isPlayerTurn(player) && pool.contains(event.getPlayer())) {
			int x = player.getLocation().getBlockX();
			int z = player.getLocation().getBlockZ();
			boolean dac = pool.isADACPattern(x, z);
			player.setFallDistance(0.0f);
			if (dacPlayer.mustConfirmate()) {
				if (dac) {
					send(DACMessage.GameDACConfirmation.format(dacPlayer.getDisplayName()));
					send(DACMessage.GameDACConfirmation2);
				} else {
					send(DACMessage.GameConfirmation.format(dacPlayer.getDisplayName()));
				}
				tpAfterJump(dacPlayer);
				onPlayerWin(dacPlayer);
			} else {
				if (dac) {
					send(DACMessage.GameDAC.format(dacPlayer.getDisplayName()));
					dacPlayer.winLive();
					send(DACMessage.GameLivesAfterDAC.format(dacPlayer.getLives()));
				} else {
					send(DACMessage.GameJumpSuccess.format(dacPlayer.getDisplayName()));
				}
				tpAfterJump(dacPlayer);
				if (dac) {
					pool.putDACColumn(x, z, dacPlayer.getColor());
				} else {
					pool.putColumn(x, z, dacPlayer.getColor());
				}
				nextTurn();
			}
		}
	}

	public void onPlayerDamage(EntityDamageEvent event) {
		Player player = (Player)event.getEntity();
		if (isPlayerTurn(player)) {
			int health = player.getHealth(); 
			if (health == 20) {
				player.damage(1);
				player.setHealth(20);
			} else {
				player.setHealth(health + 1);
				player.damage(1);
			}
			event.setCancelled(true);
			DACPlayer dacPlayer = wrapPlayer(player);
			send(DACMessage.GameJumpFail.format(dacPlayer.getDisplayName()));
			if (dacPlayer.mustConfirmate()) {
				send(DACMessage.GameConfirmationFail);
				for (DACPlayer playerWhoLost : playersWhoLostLastTurn.keySet()) {
					playerWhoLost.resetLives();
				}
				playersWhoLostLastTurn = new LinkedHashMap<DACPlayer, Location>();
				dacPlayer.setMustConfirmate(false);
				tpAfterJump(dacPlayer);
				nextTurn();
			} else {
				dacPlayer.looseLive();
				if (dacPlayer.hasLost()) {
					playersWhoLostLastTurn.put(dacPlayer, player.getLocation());
					onPlayerLoss(dacPlayer, false);
					if (DAC.getDACConfig().getTpAfterFail()) {
						dacPlayer.tpToStart(DAC.getDACConfig().getTpAfterFailDelay());
					}
				} else {
					send(DACMessage.GameLivesAfterFail.format(dacPlayer.getLives()));
					tpAfterJump(dacPlayer);
					nextTurn();
				}
			}
		}
	}

	public void onPlayerQuit(Player player) {
		onPlayerQuit(wrapPlayer(player));
	}
	
	public void onPlayerQuit(DACPlayer dacPlayer) { 
		if (!dacPlayer.hasLost()) {
			send(DACMessage.GamePlayerQuit.format(dacPlayer.getDisplayName()));
			dacPlayer.looseAllLives();
			onPlayerLoss(dacPlayer, true);
		}
	}

	public void onPlayerLoss(DACPlayer player, boolean force) {
		DACPlayer lastPlayer = getLastPlayer();
		if (lastPlayer != null) {
			if (!force && lastPlayer.getIndex() > player.getIndex() && lastPlayer.getLives() == 0) {
				lastPlayer.setMustConfirmate(true);
				send(DACMessage.GameMustConfirmate.format(lastPlayer.getDisplayName()));
				nextTurn();
			} else {
				onPlayerWin(lastPlayer);
			}
		} else {
			if (isPlayerTurn(player)) { nextTurn(); }
		}
	}

	public void onPlayerWin(DACPlayer player) {
		send(DACMessage.GameFinished);
		for (DACPlayer dacPlayer : playersWhoLostLastTurn.keySet()) {
			lostOrder.add(dacPlayer.getIndex());
		}

		send(DACMessage.GameWinner.format(player.getDisplayName()));

		int i=2;
		for (int index=lostOrder.size()-1 ; index>=0; index--) {
			String name = players[lostOrder.get(index)].getDisplayName();
			send(DACMessage.GameRank.format(i, name));
			i++;
		}
		DAC.removeGame(this);
		if (DAC.getDACConfig().getResetOnEnd()) {
			arena.getPool().reset();
		}
	}

	public void stop() {
		if (DAC.getDACConfig().getResetOnEnd()) {
			arena.getPool().reset();
		}
		send(DACMessage.GameStopped);
	}

	private void displayLives(Player player, DACPlayer dacPlayer) {
		if (!dacPlayer.hasLost()) {
			String name = dacPlayer.getDisplayName();
			int lives = dacPlayer.getLives();
			player.sendMessage(DACMessage.GameDisplayLives.format(name, lives));
		}
	}

	public void displayLives(Player player) {
		for (DACPlayer dacPlayer : players) {
			displayLives(player, dacPlayer);
		}
	}

	public boolean displayLives(Player player, String name) {
		List<Player> list = Bukkit.getServer().matchPlayer(name);
		if (list.size() != 1) { return false; }
		DACPlayer dacPlayer = wrapPlayer(list.get(0));
		if (dacPlayer == null) { return false; }
		displayLives(player, dacPlayer);
		return true;
	}

}
