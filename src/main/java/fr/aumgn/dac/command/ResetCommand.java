package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.arenas.DACArena;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.game.Game;
import fr.aumgn.dac.stage.Stage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ResetCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 1;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		DACArena arena = DAC.getArenas().get(args[0]);
		
		if (arena == null) {
			error(DACMessage.CmdResetUnknown);
		}
		
		Stage stage = DAC.getStageManager().get(arena);
		if (!context.hasPermission("dac.game.reset") && !(stage instanceof Game)) {
			error(DACMessage.CmdResetInGame);
		}
		
		arena.getPool().reset();
		context.success(DACMessage.CmdResetSuccess);
	}

}
