package fr.aumgn.dac;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.aumgn.dac.config.DACArena;
import fr.aumgn.dac.config.Pool;

public class DACGame {
	
	private static final ChatColor G = ChatColor.DARK_PURPLE;
	private static final ChatColor S = ChatColor.GREEN;
	private static final ChatColor F = ChatColor.RED;
	private static final ChatColor N = ChatColor.GRAY;
	private static final ChatColor D = ChatColor.GOLD;
	
	private DAC plugin;
	private DACArena arena;
	private DACPlayer[] players;
	private int turn;
	private List<Integer> lostOrder;
	private LinkedHashMap<DACPlayer, Location> playersWhoLostLastTurn;
	
	public DACGame(DAC plugin, DACJoinStep joinStep) {
		this.plugin = plugin;
		this.arena = joinStep.getArena();
		ArrayList<DACPlayer> roulette = joinStep.getPlayers();
		players = new DACPlayer[roulette.size()];
		Random rand = new Random();
		for (int i=0; i< players.length; i++) {
			int j = rand.nextInt(roulette.size());
			players[i] = roulette.remove(j);
			players[i].init(this, i);
		}
		lostOrder = new ArrayList<Integer>();
		playersWhoLostLastTurn = new LinkedHashMap<DACPlayer, Location>();
		arena.getPool().reset();
		send(G + "La partie commence !");
		send(G + "Joueurs :");
		for (DACPlayer player : players) {
			send(N + String.valueOf(player.getPosition()) + ChatColor.WHITE + ". " + player.getDisplayName());
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

	private boolean isPlayerTurn(Player player) {
		return players[turn].getPlayer().equals(player);
	}

	public DACPlayer getLastPlayer() {
		int i = 0;
		DACPlayer playerLeft = null;
		for (DACPlayer player : players)
			if (!player.hasLost()) {
				playerLeft = player;
				i++; 
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
					send(dacPlayer.getDisplayName() + D + " a confirmé avec un Dé a coudre.");
					send(dacPlayer.getDisplayName() + D + "Quelle classe !");
				} else {
					send(dacPlayer.getDisplayName() + S + " a confirmé.");
				}
				onPlayerWin(dacPlayer);
			} else {
				if (dac) {
					send(dacPlayer.getDisplayName() + D + " a reussi un dé a coudre.");
					dacPlayer.winLive();
					send(G + "Il a maintenant " + N + dacPlayer.getLives() + G + " vie(s).");
				} else {
					send(dacPlayer.getDisplayName() + S + " a reussi son saut.");
				}
				dacPlayer.tpToStart();
				if (dac) {
					pool.putDACColumn(x, z, dacPlayer.getColor().getWoolColor());
				} else {
					pool.putColumn(x, z, dacPlayer.getColor().getWoolColor());
				}
				nextTurn();
			}
		}
	}

	public void onPlayerDamage(EntityDamageEvent event) {
		Player player = (Player)event.getEntity();
		if (isPlayerTurn(player)) {
			DACPlayer dacPlayer = wrapPlayer(player);
			event.setCancelled(true);
			send(dacPlayer.getDisplayName() + F + " a manqué son saut.");
			if (dacPlayer.mustConfirmate()) {
				send("Il n'a donc pas pu confirmer sa victoire.");
				for (DACPlayer playerWhoLost : playersWhoLostLastTurn.keySet()) {
					playerWhoLost.resetLives();
				}
				playersWhoLostLastTurn = new LinkedHashMap<DACPlayer, Location>();
				dacPlayer.setMustConfirmate(false);
				dacPlayer.tpToStart();
				nextTurn();
			} else {
				dacPlayer.looseLive();
				if (dacPlayer.hasLost()) {
					playersWhoLostLastTurn.put(dacPlayer, player.getLocation());
					onPlayerLoss(dacPlayer, false);
				} else {
					send(F + " il a donc perdu une vie" + G + " (" + N + 
						dacPlayer.getLives() + G + " restante(s).");
					dacPlayer.tpToStart();
					nextTurn();
				}
			}
		}
	}

	public void onPlayerQuit(Player player) {
		DACPlayer dacPlayer = wrapPlayer(player);
		send(dacPlayer.getDisplayName() + F + " a quitté le serveur, il a perdu.");
		dacPlayer.looseAllLives();
		onPlayerLoss(dacPlayer, true);
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
			nextTurn();
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
		plugin.removeGame(this);
	}
	
	public void stop() {
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
		List<Player> players = Bukkit.getServer().matchPlayer(name);
		if (players.size() != 1) {
			return false;
		}
		DACPlayer dacPlayer = wrapPlayer(players.get(0));
		if (dacPlayer == null) {
			return false;
		}
		displayLives(player, dacPlayer);
		return true;
	}

}
