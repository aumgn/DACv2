package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.config.DACArena;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class DeleteCommand extends PlayerCommandExecutor {
	
	private DAC plugin;
	
	public DeleteCommand(DAC plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		DACArena arena = plugin.getDACConfig().get(args[0]);
		if (arena == null) {
			context.error("Aucune arene ne porte ce nom");
			return true;
		}
		if (plugin.getGame(arena) != null) {
			context.error("Une partie est en cours dans cette arene.");
			return true;
		}
		DACJoinStep joinStep = plugin.getJoinStep(arena);
		if (joinStep != null) {
			plugin.removeJoinStep(joinStep);
			joinStep.stop();
		}
		plugin.getDACConfig().removeArena(arena);
		context.success("Arene supprimé avec succés.");
		return true;
	}

}
