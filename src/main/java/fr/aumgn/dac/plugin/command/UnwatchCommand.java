package fr.aumgn.dac.plugin.command;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class UnwatchCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return true;
	}
	
	@Override
	public void onPlayerCommand(Context context, String[] args) {
		if (args.length == 0) {
			for (Arena arena : DAC.getArenas()) {
				processArena(context, arena);
			}
		} else {
			for (String arg : args) {
				Arena arena = DAC.getArenas().get(arg);
				if (arena == null) {
					context.error(DACMessage.CmdWatchUnknown.format(arg));
				} else {
					processArena(context, arena);
				}				
			}
		}
	}

	private void processArena(Context context, Arena arena) {
		Stage<?> stage = DAC.getStageManager().get(arena);
		if (!(stage instanceof Game)) {
			return;
		}
		if (((Game<?>) stage).removeSpectator(context.getPlayer())) {
			context.success(DACMessage.CmdUnwatchSuccess.format(arena.getName()));
		}
	}

}
