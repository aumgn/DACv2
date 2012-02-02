package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.dac.config.DACMessage;
import fr.aumgn.utils.command.BasicCommandExecutor;

public class ReloadCommand extends BasicCommandExecutor {

	@Override
	public boolean onCommand(Context context, String[] args) {
		DAC.reloadConfig();
		DAC.reloadMessages();
		context.success(DACMessage.CmdReloadSuccess);
		return true;
	}

}
