package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArea;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.exception.InvalidRegionType;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class SelectCommand extends PlayerCommandExecutor {

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		if (args.length != 2) {
			usageError();
		}
		
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			error(DACMessage.CmdSelectUnknown);
		}
		DACArea area = null;
		DACMessage message = DACMessage.CmdSelectError;
		if (args[1].equalsIgnoreCase("pool")) {
			area = arena.getPool();
			message = DACMessage.CmdSelectSuccessPool;
		} else if (args[1].equalsIgnoreCase("start")) {
			area = arena.getStartArea();
			message = DACMessage.CmdSelectSuccessStart;
		} else {
			usageError();
		}
		
		try {
			DAC.getWorldEdit().setSelection(context.getPlayer(), area.getSelection());
			context.success(message);
		} catch (InvalidRegionType exc) {
			context.error(DACMessage.CmdSelectError);
			error(exc.getMessage());
		}
	}

}
