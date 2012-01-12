package fr.aumgn.dac;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.arenas.Pool;

public class DACGame {
	
	private static final ChatColor G = ChatColor.BLUE;
	private static final ChatColor S = ChatColor.GREEN;
	private static final ChatColor F = ChatColor.RED;
	private static final ChatColor N = ChatColor.GRAY;
	private static final ChatColor D = ChatColor.GOLD;
	
	private DACArena arena;
	private DACPlayer[] players;
	private int turn;
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
		send(G + "La partie commence !");
		send(G + "Joueurs :");
		for (DACPlayer player : players) {
			send(N.toString() + player.getPosition() + ChatColor.WHITE + ". " + player.getDisplayName());
		}
		send(G + "Enjoy !");
		turn = -1;
		nextTurn();
	}
	
	private void send(String message) {
		for (DACPlayer player : players) {
			player.getPlayer().sendMessage(message);
		}
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
		do {
			increaseTurn();
			player = players[turn];
		} while (player.hasLost());
		send(G + "C'est au tour de " + player.getDisplayName());
		player.getPlayer().teleport(arena.getDivingBoard());
	}

	private boolean isPlayerTurn(DACPlayer player) {
		return isPlayerTurn(player.getPlayer());
	}

	private boolean isPlayerTurn(Player player) {
		return players[turn].getPlayer().equals(player);
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
					send(dacPlayer.getDisplayName() + D + " a confirmé avec un Dé à coudre.");
					send(dacPlayer.getDisplayName() + D + "Quelle classe !");
				} else {
					send(dacPlayer.getDisplayName() + S + " a confirmé.");
				}
				if (DAC.getDACConfig().getTpAfterJump()) {
					dacPlayer.tpToStart(DAC.getDACConfig().getTpAfterSuccessDelay());
				}
				onPlayerWin(dacPlayer);
			} else {
				if (dac) {
					send(dacPlayer.getDisplayName() + D + " a reussi un dé à coudre.");
					dacPlayer.winLive();
					send(G + "Il/Elle a maintenant " + N + dacPlayer.getLives() + G + " vie(s).");
				} else {
					send(dacPlayer.getDisplayName() + S + " a réussi son saut.");
				}
				if (DAC.getDACConfig().getTpAfterJump()) {
					dacPlayer.tpToStart(DAC.getDACConfig().getTpAfterSuccessDelay());
				}
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
			send(dacPlayer.getDisplayName() + F + " a manqué son saut.");
			if (dacPlayer.mustConfirmate()) {
				send("Il/elle n'a donc pas pu confirmer sa victoire.");
				for (DACPlayer playerWhoLost : playersWhoLostLastTurn.keySet()) {
					playerWhoLost.resetLives();
				}
				playersWhoLostLastTurn = new LinkedHashMap<DACPlayer, Location>();
				dacPlayer.setMustConfirmate(false);
				if (DAC.getDACConfig().getTpAfterJump()) {
					dacPlayer.tpToStart(DAC.getDACConfig().getTpAfterSuccessDelay());
				}
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
					send(F + " Il/elle a donc perdu une vie" + G + " (" + N + 
						dacPlayer.getLives() + G + " restante(s).");
					if (DAC.getDACConfig().getTpAfterJump()) {
						dacPlayer.tpToStart(DAC.getDACConfig().getTpAfterSuccessDelay());
					}
					nextTurn();
				}
			}
		}
	}

	public void onPlayerQuit(Player player) {
		DACPlayer dacPlayer = wrapPlayer(player);
		if (!dacPlayer.hasLost()) {
			send(dacPlayer.getDisplayName() + F + " a quitté la partie.");
			dacPlayer.looseAllLives();
			onPlayerLoss(dacPlayer, true);
		}
	}
	
	public void onPlayerLoss(DACPlayer player, boolean force) {
		DACPlayer lastPlayer = getLastPlayer();
		if (lastPlayer != null) {
			if (!force && lastPlayer.getIndex() > player.getIndex() && lastPlayer.getLives() == 0) {
				lastPlayer.setMustConfirmate(true);
				send(lastPlayer.getDisplayName() + G + " doit confirmer.");
				nextTurn();
			} else {
				onPlayerWin(lastPlayer);
			}
		} else {
			if (isPlayerTurn(player)) { nextTurn(); }
		}
	}
	
	public void onPlayerWin(DACPlayer player) {
		send(G + "La partie est terminée.");
		for (DACPlayer dacPlayer : playersWhoLostLastTurn.keySet()) {
			lostOrder.add(dacPlayer.getIndex());
		}
		
		send("Gagnant : " + player.getDisplayName());
		
		int i=2;
		for (int index=lostOrder.size()-1 ; index>=0; index--) {
			send(N.toString() + i + ChatColor.WHITE + ". " + players[lostOrder.get(index)].getDisplayName());
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
		send(G + "La partie a été arretée.");
	}

	private void displayLives(Player player, DACPlayer dacPlayer) {
		if (!dacPlayer.hasLost()) {
			String message = dacPlayer.getDisplayName() + G + ": ";
			message += N.toString() + dacPlayer.getLives() + G + " vie(s)";
			player.sendMessage(message);
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
