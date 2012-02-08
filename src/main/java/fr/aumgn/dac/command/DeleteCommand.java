package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.arenas.DACArena;
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
		if (DAC.getGame(arena) != null) {
			error(DACMessage.CmdDeleteInGame);
		}
		DACJoinStep joinStep = DAC.getJoinStep(arena);
		if (joinStep != null) {
			DAC.removeJoinStep(joinStep);
			joinStep.stop();
		}
		DAC.getArenas().removeArena(arena);
		context.success(DACMessage.CmdDeleteSuccess);
	}

}
