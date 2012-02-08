package fr.aumgn.dac.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class KickCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		Player sender = context.getPlayer(); 
		
		DACGame game = DAC.getGame(sender);
		if (game != null) {
			List<Player> list = matchPlayer(context, args[0]);
			if (list == null) { return true; }
			
			for (Player player : list) {
				if (game.contains(player)) {
					game.onPlayerQuit(player);
				}
			}
			
			return true;
		}
		
		DACJoinStep joinStep = DAC.getJoinStep(sender);
		if (joinStep != null) {
			List<Player> list = matchPlayer(context, args[0]);
			if (list == null) { return true; }
			
			for (Player player : list) {
				if (joinStep.contains(player)) {
					joinStep.remove(player);
				}
			}
			
			return true;
		}
		
		context.error(DACMessage.CmdKickNotInGame);
		return true;
	}
	
	public List<Player> matchPlayer(Context context, String arg) {
		List<Player> list = Bukkit.matchPlayer(arg);
		if (list.isEmpty()) {
			context.error(DACMessage.CmdKickNoPlayerFound);
			return null;
		}
		return list;
	}
	
}
