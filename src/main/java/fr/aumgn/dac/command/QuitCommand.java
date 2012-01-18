package fr.aumgn.dac.command;

import org.bukkit.entity.Player;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class QuitCommand extends PlayerCommandExecutor {
	
	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length > 0) {
			return false;
		}
		Player player = context.getPlayer(); 
		DACJoinStep joinStep = DAC.getJoinStep(player);
		if (joinStep != null) {
			joinStep.remove(player);
			return true;
		}
		DACGame game = DAC.getGame(player);
		if (game != null) {
			game.onPlayerQuit(player);
			return true;
		}
		context.error(DACMessage.CmdQuitNotInGame);
		return true;
	}

}
