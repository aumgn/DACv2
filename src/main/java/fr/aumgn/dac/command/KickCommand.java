package fr.aumgn.dac.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class KickCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		Player sender = context.getPlayer(); 
		
		DACGame game = DAC.getGame(sender);
		if (game == null) {
			context.error(DACMessage.CmdKickNotInGame);
			return true;
		}
		
		List<Player> list = Bukkit.matchPlayer(args[0]);
		if (list.isEmpty()) {
			context.error(DACMessage.CmdKickNoPlayerFound);
			return true;
		}

		for (Player player : list) {
			game.onPlayerQuit(player);
		}
		return true;
	}
	
}
