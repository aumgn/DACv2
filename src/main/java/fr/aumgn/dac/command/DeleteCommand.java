package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arena.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class DeleteCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 1;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			error(DACMessage.CmdDeleteUnknown);
		}
		if (DAC.getStageManager().hasStage(arena)) {
			error(DACMessage.CmdDeleteInGame);
		}
		DAC.getArenas().removeArena(arena);
		context.success(DACMessage.CmdDeleteSuccess);
	}

}
