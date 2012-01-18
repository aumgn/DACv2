package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.utils.command.BasicCommandExecutor;

public class ReloadCommand extends BasicCommandExecutor {

	@Override
	public boolean onCommand(Context context, String[] args) {
		DAC.reloadDACConfig();
		DAC.reloadLang();
		context.success("Configuration mise a jour.");
		return true;
	}

}
