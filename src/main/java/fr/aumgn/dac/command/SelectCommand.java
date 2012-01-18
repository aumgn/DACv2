package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArea;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.DACException.InvalidRegionType;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class SelectCommand extends PlayerCommandExecutor {
	
	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		if (args.length != 2) { return false; }
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			context.error(DACMessage.CmdSelectError);
			return true;
		}
		DACArea area;
		DACMessage message;
		if (args[1].equalsIgnoreCase("pool")) {
			area = arena.getPool();
			message = DACMessage.CmdSelectSuccessPool;
		} else if (args[1].equalsIgnoreCase("start")) {
			area = arena.getStartArea();
			message = DACMessage.CmdSelectSuccessStart;
		} else {
			return false;
		}
		try {
			DAC.getWorldEdit().setSelection(context.getPlayer(), area.getSelection());
			context.success(message);
			return true;
		} catch (InvalidRegionType exc) {
			context.error(DACMessage.CmdSelectError);
			context.error(exc.getMessage());
			return true;
		}
	}
	
}
