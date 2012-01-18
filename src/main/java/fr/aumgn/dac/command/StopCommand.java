package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StopCommand extends PlayerCommandExecutor {

	@Override
	public boolean onPlayerCommand(Context context, String[] args) {
		DACGame game = DAC.getGame(context.getPlayer());
		if (game == null) {
			DACJoinStep joinStep = DAC.getJoinStep(context.getPlayer());
			if (joinStep == null) {
				context.error(DACMessage.CmdStopNoGameToStop);
				return true;
			}
			joinStep.stop();
			DAC.removeJoinStep(joinStep);
			return true;
		}
		game.stop();
		DAC.removeGame(game);
		return true;
	}

}
