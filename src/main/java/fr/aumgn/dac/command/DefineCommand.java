package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class DefineCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 1;		
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		if (DAC.getArenas().get(args[0]) != null) {
			error(DACMessage.CmdDefineExists);
		}
		DAC.getArenas().createArena(args[0], context.getPlayer().getWorld());
		context.success(DACMessage.CmdDefineSuccess);
	}

}
