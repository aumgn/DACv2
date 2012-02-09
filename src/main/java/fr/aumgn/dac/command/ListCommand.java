package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.dac.player.DACPlayer;
import fr.aumgn.dac.stage.Stage;
import fr.aumgn.utils.command.PlayerCommandExecutor;

public class ListCommand extends PlayerCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 0;
	}

	@Override
	public void onPlayerCommand(Context context, String[] args) {
		Stage stage = DAC.getStageManager().getPlayer(context.getPlayer()).getStage();
		for (DACPlayer playerinStage : stage.getPlayers()) {
			context.send(" " + playerinStage.getDisplayName());
		}

		error(DACMessage.CmdLivesNotInGame);
	}

}
