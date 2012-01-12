package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class DefineCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		if (DAC.getArenas().get(args[0]) != null) {
			context.error("Une arène portant ce nom existe deja");
			return true;
		}
		DAC.getArenas().createArena(args[0], context.getPlayer().getWorld());
		context.success("Arène créée avec succés");
		return true;
	}

}
