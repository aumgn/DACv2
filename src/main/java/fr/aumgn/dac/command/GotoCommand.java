package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class GotoCommand extends PlayerCommandExecutor {

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) {
			usageError();
		}
		
		DACGame game = DAC.getGame(context.getPlayer());
		if (game == null) {
			error(DACMessage.CmdGotoNotInGame);
		}
		if (args[0].equalsIgnoreCase("diving")) {
			context.getPlayer().teleport(game.getArena().getDivingBoard().getLocation());
		} else if (args[0].equalsIgnoreCase("start")) {
			game.wrapPlayer(context.getPlayer()).tpToStart();
		} else {
			usageError();
		}
		context.success(DACMessage.CmdGotoSuccess);
	}

}
