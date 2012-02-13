package fr.aumgn.dac.command;

import java.util.Map;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arena.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.game.options.GameOptions;
import fr.aumgn.utils.command.BasicCommandExecutor;

public class OptionsCommand extends BasicCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		if (args.length == 1) {
			return true;
		}
		return args.length > 2 && (
				args[1].equalsIgnoreCase("set") ||
				args[1].equalsIgnoreCase("rm"));
	}
	
	@Override
	public void onCommand(Context context, String[] args) {
		DACArena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			error(DACMessage.CmdOptionsUnknown);
		}
		
		GameOptions options = arena.getOptions();

		if (args.length == 1) {
			context.send(DACMessage.CmdOptionsList);
			for (Map.Entry<String, String> option : options) {
				context.send(DACMessage.CmdOptionsOption.format(option.getKey(), option.getValue()));
			}
			return;
		}
		
		if (args[1].equalsIgnoreCase("set")) {
			for (int i = 2; i < args.length; i++) {
				String[] option = args[i].split(":");
				if (option.length == 2) {
					options.set(option[0], option[1]);
				}
			}
			arena.updated();
			context.send(DACMessage.CmdOptionsAddSuccess);
		} else if (args[1].equalsIgnoreCase("rm")) {
			for (int i = 2; i < args.length; i++) {
				options.remove(args[i]);
			}
			arena.updated();
			context.send(DACMessage.CmdOptionsRemoveSuccess);
		}
	}

}
