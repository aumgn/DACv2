package fr.aumgn.dac.plugin.command;

import fr.aumgn.dac.api.DAC;
import fr.aumgn.dac.api.arena.Arena;
import fr.aumgn.dac.api.config.DACMessage;
import fr.aumgn.dac.api.game.Game;
import fr.aumgn.dac.api.game.mode.DACGameMode;
import fr.aumgn.dac.api.game.mode.GameMode;
import fr.aumgn.dac.api.stage.Stage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ResetCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 1;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		Arena arena = DAC.getArenas().get(args[0]);
		
		if (arena == null) {
			error(DACMessage.CmdResetUnknown);
		}
		
		Stage<?> stage = DAC.getStageManager().get(arena);
		if (stage instanceof Game) {
			if (!context.hasPermission("dac.game.reset")) {
				GameMode<?> mode = ((Game<?>)stage).getMode();
				if (!mode.getClass().getAnnotation(DACGameMode.class).allowPoolReset()) {
					error(DACMessage.CmdResetInGame);
				}
			}
		}
		
		arena.getPool().reset();
		context.success(DACMessage.CmdResetSuccess);
	}

}
