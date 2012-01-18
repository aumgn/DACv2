package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class GotoCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		DACGame game = DAC.getGame(context.getPlayer());
		if (game == null) {
			context.error(DACMessage.CmdGotoNotInGame);
			return true;
		}
		if (args[0].equalsIgnoreCase("diving")) {
			context.getPlayer().teleport(game.getArena().getDivingBoard());
		} else if (args[0].equalsIgnoreCase("start")) {
			game.wrapPlayer(context.getPlayer()).tpToStart();
		} else {
			return false;
		}
		context.success(DACMessage.CmdGotoSuccess);
		return true;
	}

}
