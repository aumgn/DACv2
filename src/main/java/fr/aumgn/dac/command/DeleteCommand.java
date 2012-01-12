package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class DeleteCommand extends PlayerCommandExecutor {
	
	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			context.error("Aucune arène ne porte ce nom");
			return true;
		}
		if (DAC.getGame(arena) != null) {
			context.error("Une partie est en cours dans cette arène.");
			return true;
		}
		DACJoinStep joinStep = DAC.getJoinStep(arena);
		if (joinStep != null) {
			DAC.removeJoinStep(joinStep);
			joinStep.stop();
		}
		DAC.getArenas().removeArena(arena);
		context.success("Arène supprimée avec succés.");
		return true;
	}

}
