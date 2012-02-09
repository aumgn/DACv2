package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
//import fr.aumgn.dac.DACGame;
//import fr.aumgn.dac.DACJoinStep;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.stage.Stage;
import fr.aumgn.dac.stage.StageManager;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class StopCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 0;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		StageManager stageManager = DAC.getStageManager();
		Stage stage = stageManager.getPlayer(context.getPlayer()).getStage();
		if (stage == null) {
			error(DACMessage.CmdStopNoGameToStop);
		}

		stage.stop();
		//stageManager.remove(stage);
	}

}
