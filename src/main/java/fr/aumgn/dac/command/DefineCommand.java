package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class DefineCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 1) { return false; }
		if (DAC.getArenas().get(args[0]) != null) {
			context.error(DACMessage.CmdDefineExists);
			return true;
		}
		DAC.getArenas().createArena(args[0], context.getPlayer().getWorld());
		context.success(DACMessage.CmdDefineSuccess);
		return true;
	}

}
