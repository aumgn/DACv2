package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.BasicCommandExecutor;

public class ReloadCommand extends BasicCommandExecutor {

	@Override
	public boolean checkUsage(String[] args) {
		return args.length == 0;
	}

	@Override
	public void onCommand(Context context, String[] args) {
		if (args.length > 0) {
			usageError();
		}

		DAC.reloadConfig();
		DAC.reloadMessages();
		context.success(DACMessage.CmdReloadSuccess);
	}

}
