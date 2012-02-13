package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arena.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.BasicCommandExecutor;

public class ModesCommand extends BasicCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		if (args.length < 2) {
			return true;
		}
		return args.length == 3 && (
				args[1].equalsIgnoreCase("add") ||
				args[1].equalsIgnoreCase("rm"));
	}
	
	@Override
	public void onCommand(Context context, String[] args) {
		if (args.length == 0) {
			context.send(DACMessage.CmdModesList);
			for (String modeName : DAC.getModes().getNames()) {
				context.send(DACMessage.CmdModesMode.format(modeName));
			}
			return;
		}
		
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			error(DACMessage.CmdModesUnknown);
		}
		
		if (args.length == 1) {
			context.send(DACMessage.CmdModesList);
			for (String modeName : arena.getModes()) {
				context.send(DACMessage.CmdModesMode.format(modeName));
			}
			return;
		}
		
		if (args[1].equalsIgnoreCase("add")) {
			arena.addMode(args[2]);
			context.send(DACMessage.CmdModesAddSuccess);
		} else if (args[1].equalsIgnoreCase("rm")) {
			arena.removeMode(args[2]);
			context.send(DACMessage.CmdModesRemoveSuccess);
		}
	}

}
