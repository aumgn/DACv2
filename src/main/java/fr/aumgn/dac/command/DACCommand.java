package fr.aumgn.dac.command;

import fr.aumgn.dac.DAC;
import fr.aumgn.utils.command.BasicCommandExecutor;
import fr.aumgn.utils.command.CommandDispatcher;

public class DACCommand extends CommandDispatcher {
	
	public DACCommand() {
		super("dac");
		registerCommand("reload", new BasicCommandExecutor() {
			@Override
			public boolean onCommand(Context context, String[] args) {
				DAC.reloadDACConfig();
				context.success("Configuration mise a jour.");
				return true;
			}
		});
		registerCommand("define", new DefineCommand());
		registerCommand("delete", new DeleteCommand());
		registerCommand("set", new SetCommand());
		registerCommand("select", new SelectCommand());
		registerCommand("join", new JoinCommand());
		registerCommand("start", new StartCommand());
		registerCommand("stop", new StopCommand());
		registerCommand("reset", new ResetCommand());
		registerCommand("lives", new LivesCommand());
		registerCommand("goto", new GotoCommand());
		registerCommand("quit", new QuitCommand());
		registerCommand("colors", new ColorsCommand());
	}

}
