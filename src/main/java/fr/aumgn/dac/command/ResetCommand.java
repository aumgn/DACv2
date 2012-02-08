package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ResetCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 1;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			error(DACMessage.CmdResetUnknown);
		}
		if (!context.hasPermission("dac.game.reset") && DAC.getGame(arena) != null) {
			error(DACMessage.CmdResetInGame);
		}
		arena.getPool().reset();
		context.success(DACMessage.CmdResetSuccess);
	}

}
