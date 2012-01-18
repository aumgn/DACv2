package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class LivesCommand extends PlayerCommandExecutor {
	
	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length > 2) { return false; }
		DACGame game = DAC.getGame(context.getPlayer());
		if (game == null) {
			context.error(DACMessage.CmdLivesNotInGame);
			return true;
		}
		if (args.length == 0) {
			game.displayLives(context.getPlayer());
		} else {
			if (!game.displayLives(context.getPlayer(), args[0])) {			
				context.error(DACMessage.CmdLivesUnknownPlayer);
			}
		}
		return true;
	}

}
