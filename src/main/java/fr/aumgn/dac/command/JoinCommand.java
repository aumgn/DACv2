package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class JoinCommand extends PlayerCommandExecutor {
	
	private DAC plugin;

	public JoinCommand(DAC plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		Player player = context.getPlayer();
		if (plugin.isPlayerInGame(player)) {
			context.error("Vous ne pouvez pas rejoindre plusieurs parties.");
			return true;	
		}
		DACArena arena = plugin.getArenas().get(player.getLocation());
		if (arena == null) {
			context.error("Vous devez vous trouver dans la zone de départ d'un DAC pour utiliser cette commande.");
			return true;
		}
		if (plugin.getGame(arena) != null) {
			context.error("Une partie est déjà en cours dans cette arène.");
			return true;
		}
		DACJoinStep joinStep = plugin.getJoinStep(arena);
		if (joinStep == null) {
			joinStep = new DACJoinStep(plugin, arena);
			plugin.setJoinStep(joinStep);
		}
		if (joinStep.isMaxReached()) {
			context.error("Il y a déjà 8 joueurs dans la partie");
			return true;
		}
		joinStep.addPlayer(player, args);
		return true;
	}

}
