package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.stage.Stage;
import fr.aumgn.dac.stage.StagePlayer;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ListCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 0;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		Stage<?> stage = DAC.getStageManager().get(context.getPlayer());
		
		if (stage == null) {
			error(DACMessage.CmdLivesNotInGame);
		}
		
		for (StagePlayer playerInStage : stage.getPlayers()) {
			context.send(playerInStage.formatForList());
		}
	}

}
