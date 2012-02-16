package fr.aumgn.dac.plugin.command;

import java.util.Arrays;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.fillstrategy.FillStrategy;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class FillCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length > 1;
	}
	
	@Override
	public void onPlayerCommand(Context context, String[] args) {
		Arena arena = DAC.getArenas().get(args[0]);
		if (arena == null) {
			error(DACMessage.CmdModesUnknown);
		}
		
		FillStrategy strategy = DAC.getFillStrategies().get(args[1]);
		if (strategy == null) {
			error("Unknown fill stratey");
		}
		
		String[] fillArgs = Arrays.copyOfRange(args, 2, args.length);
		arena.getPool().fillWith(strategy, fillArgs);
	}

}
