package fr.aumgn.dac.api;

import fr.aumgn.utils.command.BasicCommandExecutor;

public interface DACCommand {

	void registerCommand(String name, BasicCommandExecutor executor);
	
}
