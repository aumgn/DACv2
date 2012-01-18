package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ResetCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			context.error(DACMessage.CmdResetUnknown);
			return true;
		}
		if (DAC.getGame(arena) != null) {
			context.error(DACMessage.CmdResetInGame);
			return true;
		}
		arena.getPool().reset();
		context.success(DACMessage.CmdResetSuccess);
		return true;
	}

}
