package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.DACGame;
import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StopCommand extends PlayerCommandExecutor {

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		if (args.length > 0) {
			
		}
		
		DACGame game = DAC.getGame(context.getPlayer());
		if (game == null) {
			DACJoinStep joinStep = DAC.getJoinStep(context.getPlayer());
			if (joinStep == null) {
				error(DACMessage.CmdStopNoGameToStop);
			}
			joinStep.stop();
			DAC.removeJoinStep(joinStep);
		}
		
		game.stop();
		DAC.removeGame(game);
	}

}
