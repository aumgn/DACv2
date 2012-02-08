package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.BasicCommandExecutor;

public class ArenasCommand extends BasicCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 0;
	}
	
	@Override
	public void onCommand(Context context, String[] args) {
		context.send(DACMessage.CmdArenasList);
		for (DACArena arena : DAC.getArenas()) {
			context.send(DACMessage.CmdArenasArena.format(arena.getName(), arena.getWorld().getName()));
		}
	}

}
